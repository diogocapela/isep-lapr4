package rest.routes;

import rest.HTTPMessage;
import rest.HTTPServer;
import rest.Settings;
import rest.output.SE06Output;
import utils.validate_transform.XSDValidator;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;

public class SE06Route extends Route {
    private final static String OUTPUT_XSD = "rest/src/main/resources/SE06/SE06_Output.xsd";
    private final static String OUTPUT_XHTML_XSL = "rest/src/main/resources/SE06/SE06_Output_XHTML.xsl";
    private final static String OUTPUT_JSON_XSL = "rest/src/main/resources/SE06/SE06_Output_JSON.xsl";

    private HTTPServer housingServer;

    public SE06Route(HTTPServer server, HTTPMessage request, HTTPMessage response) throws IOException {
        super(request, response);
        this.housingServer = server;
    }

    public void conhecerDisponibilidadeServicoAvaliacaoRisco() {
        String endpoint = request.getURI();
        SE06Output output;
        String temp = housingServer.getServerLoad();
        output = new SE06Output(temp);
        System.out.println(output);

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

    private String buildXMLString(SE06Output o) {
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
