package rest.routes;

import controllers.CompararAvaliacaoDeObjectoFromXMLController;
import rest.HTTPMessage;
import rest.Settings;
import rest.output.SE04Output;
import utils.validate_transform.XSDValidator;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class SE04Route extends Route {

    private final static String INPUT_XSD = "rest/src/main/resources/SE04/SE04_Input.xsd";
    private final static String OUTPUT_XSD = "rest/src/main/resources/SE04/SE04_Output.xsd";
    private final static String OUTPUT_XHTML_XSL = "rest/src/main/resources/SE04/SE04_Output_XHTML.xsl";
    private final static String OUTPUT_JSON_XSL = "rest/src/main/resources/SE04/SE04_Output_JSON.xsl";

    public SE04Route(HTTPMessage request, HTTPMessage response) throws IOException {
        super(request, response);
    }

    public void compararAvaliacaoObjecto() {
        // convert input to XML
        String xmlInputString = formatUtils.buildXMLInputString(new String(request.getContent()), request.getContentType());
        if (xmlInputString == null) {
            response.setResponseStatus(Settings.STATUS_BAD_REQUEST);
            response.setContentFromString(formatUtils.buildOutputString("HTTP request's body is neither in XML or JSON format", getXSLErrorFileName()), Settings.xmlOutputFormat);
            return;
        }

        // validate input in XML
        try {
            new XSDValidator(xmlInputString, INPUT_XSD).validateXMLwithXSD();
        } catch (IOException e) {
            e.printStackTrace();
            response.setResponseStatus(Settings.STATUS_BAD_REQUEST);
            response.setContentFromString(
                formatUtils.buildOutputString(
                    "ERROR validating input XML against XSD",
                    getXSLErrorFileName()),
                Settings.xmlOutputFormat);
            return;
        }

        // Processamento da Comparacao de Avaliacao do Objecto Com Duas Matrizes
        List<String> message = new CompararAvaliacaoDeObjectoFromXMLController().comparaAvaliacao(xmlInputString);
        SE04Output output = null;
        if (message == null) {
            response.setResponseStatus(Settings.STATUS_BAD_REQUEST);
            response.setContentFromString(
                formatUtils.buildOutputString(
                    "ERROR ao comparar avaliações de objecto",
                    getXSLErrorFileName()),
                Settings.xmlOutputFormat);
            return;
        } else {
            output = new SE04Output(message);
        }

        String xmlOutputString = buildXMLString(output);

        // validate output in XML
        try {
            new XSDValidator(xmlOutputString, OUTPUT_XSD).validateXMLwithXSD();
        } catch (IOException e) {
            e.printStackTrace();
            response.setResponseStatus(Settings.STATUS_BAD_REQUEST);
            response.setContentFromString(
                formatUtils.buildOutputString(
                    "ERROR validating output XML against XSD",
                    getXSLErrorFileName()),
                Settings.xmlOutputFormat);
            return;
        }

        // build final response
        response.setResponseStatus(Settings.STATUS_OK);
        String outputString = formatUtils.buildOutputString(xmlOutputString, getXSLFileName());
        response.setContentFromString(outputString, outputFormat);
    }

    public String buildXMLString(SE04Output o) {
        try {
            String result;
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            JAXB.marshal(o, sw);
            result = sw.toString();
            result = result.replace("<output>", "<output xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">");
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
