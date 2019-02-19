package rest.routes;

import repository.CasoRepository;
import rest.HTTPMessage;
import rest.HTTPServer;
import rest.Settings;
import rest.output.SE07Output;
import utils.validate_transform.XSDValidator;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;


public class SE07Route extends Route {
    private final static String OUTPUT_XSD = "rest/src/main/resources/SE07/SE07_Output.xsd";
    private final static String OUTPUT_XHTML_XSL = "rest/src/main/resources/SE07/SE07_Output_XHTML.xsl";
    private final static String OUTPUT_JSON_XSL = "rest/src/main/resources/SE07/SE07_Output_JSON.xsl";

    private HTTPServer housingServer;
    private CasoRepository casoRepository = new CasoRepository();
    //private List<CasoDTO> resulDTO;

    public SE07Route(HTTPServer server, HTTPMessage request, HTTPMessage response) throws IOException {
        super(request, response);
        this.housingServer = server;
    }

    public void relatorioestatisticodadisponibilidade() {
        //Leitura dos valores do server
        List obj;
        obj = housingServer.serverReport();
        Date start = (Date) obj.get(0);
        int acepted = (int) obj.get(1);
        int rejected = (int) obj.get(2);
        int active = (int) obj.get(3);
        int maxCapacityPer60Seconds = (int) obj.get(4);

        //Calculo da tempo medio de pedidos
        Date serverStartDatenow = new Date();
        int average = Math.toIntExact((serverStartDatenow.getTime() - start.getTime()) / acepted);

        //Construção do XML
        SE07Output output = new SE07Output(start, acepted, rejected, active, average, maxCapacityPer60Seconds);
        String xmlOutputString = buildXMLString(output);
        System.out.println(xmlOutputString);

        // validate raconsole.output in XML
        boolean invalidXML = false;
        try {
            invalidXML = !(new XSDValidator(xmlOutputString, OUTPUT_XSD).validateXMLwithXSD());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (invalidXML) {
            response.setResponseStatus(Settings.STATUS_BAD_REQUEST);
            response.setContentFromString(formatUtils.buildOutputString("ERROR validating raconsole.output XML against XSD", getXSLErrorFileName()), Settings.xmlOutputFormat);
            return;
        }
        response.setResponseStatus(Settings.STATUS_OK);
        String outputString = formatUtils.buildOutputString(xmlOutputString, getXSLFileName());
        response.setContentFromString(outputString, outputFormat);
    }

    private String buildXMLString(SE07Output o) {
        try {
            String result;
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            JAXB.marshal(o, sw);
            result = sw.toString();
            result = result.replace("<raconsole.output>", "<raconsole.output xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">");
            //System.out.println(result);
            return result;

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getXSLFileName() {
        if (outputFormat.equals(Settings.xmlOutputFormat)) {
            return null;
        }
        if (outputFormat.equals(Settings.xhtmlOutputFormat)) {
            return OUTPUT_XHTML_XSL;
        } else if (outputFormat.equals(Settings.jsonOutputFormat)) {
            return OUTPUT_JSON_XSL;
        }

        return null;
    }
}
