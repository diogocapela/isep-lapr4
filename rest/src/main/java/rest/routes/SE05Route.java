package rest.routes;

import controllers.ProcessCasoFromXMLController;
import rest.HTTPMessage;
import rest.Settings;
import rest.output.SE05Output;
import utils.validate_transform.XSDValidator;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class SE05Route extends Route {

    private final static String INPUT_XSD = "rest/src/main/resources/SE05/SE05_Input.xsd";
    private final static String OUTPUT_XSD = "rest/src/main/resources/SE05/SE05_Output.xsd";
    private final static String OUTPUT_XHTML_XSL = "rest/src/main/resources/SE05/SE05_Output_XHTML.xsl";
    private final static String OUTPUT_JSON_XSL = "rest/src/main/resources/SE05/SE05_Output_JSON.xsl";

    public SE05Route(HTTPMessage request, HTTPMessage response) throws IOException {
        super(request, response);
    }

    public void submeterConjuntoPedidoAvaliacao() {
        response.setResponseStatus(null);

        // 1. Convert the input to XML (if it is not XML already)
        //==================================================================================
        String xmlInputString = formatUtils.buildXMLInputString(new String(request.getContent()), request.getContentType());
        // If there was an error return the appropriate status code
        if (xmlInputString == null) {
            response.setResponseStatus(Settings.STATUS_BAD_REQUEST);
            response.setContentFromString(formatUtils.buildOutputString("ERROR: The HTTP request body need to be in XML or JSON format!", getXSLErrorFileName()), outputFormat);
            return;
        }

        // 2. Validate the XML input
        //==================================================================================
        try {
            boolean invalidXML = !(new XSDValidator(xmlInputString, INPUT_XSD).validateXMLwithXSD());
            if (invalidXML) {
                response.setResponseStatus(Settings.STATUS_BAD_REQUEST);
                response.setContentFromString(formatUtils.buildOutputString("ERROR: Errror validating the XML input against the XSD!", getXSLErrorFileName()), outputFormat);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // 3. Split the XML input and build one for each caso
        //==================================================================================
        List<String> casosData = new ArrayList<>();

        String curated = xmlInputString.split("<casos>")[1].split("</casos>")[0];
        for (String casoData : curated.split("<caso>")) {
            if (!casoData.equals("")) {
                casosData.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?><caso>" + casoData);
            }
        }

        // 4. Feed each of the XML caso data to the respective controller
        //==================================================================================
        List<String> result = new ArrayList<>();
        for (String c : casosData) {
            result.add(new ProcessCasoFromXMLController().processarCaso(c));
        }

        // 5. Check for errors
        //==================================================================================
        if (result.get(0) == null) {
            response.setResponseStatus(Settings.STATUS_BAD_REQUEST);
            response.setContentFromString(formatUtils.buildOutputString("ERROR: Evaluating casos!", getXSLErrorFileName()), outputFormat);
            return;
        }

        // 6. Build the XML output string
        //==================================================================================
        SE05Output output = new SE05Output(result);
        String xmlOutputString = buildXMLString(output);

        // 7. Validate the XML output string against the XSD output validator
        //==================================================================================
        try {
            boolean invalidXML = !(new XSDValidator(xmlOutputString, OUTPUT_XSD).validateXMLwithXSD());
            if (invalidXML) {
                response.setResponseStatus(Settings.STATUS_BAD_REQUEST);
                response.setContentFromString(formatUtils.buildOutputString("ERROR: Error validating the output XML against the output XSD!", getXSLErrorFileName()), outputFormat);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 8. Set the appropriate response
        //==================================================================================
        response.setResponseStatus(Settings.STATUS_OK);
        String outputString = formatUtils.buildOutputString(xmlOutputString, getXSLFileName());
        response.setContentFromString(outputString, outputFormat);
    }

    public String buildXMLString(SE05Output o) {
        try {
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            JAXB.marshal(o, sw);
            String result = sw.toString();
            result = result.replace("<output>", "<output xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">");
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
