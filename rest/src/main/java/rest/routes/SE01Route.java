package rest.routes;

import controllers.ProcessCasoFromXMLController;
import rest.HTTPMessage;
import rest.Settings;
import rest.output.SE01Output;
import utils.validate_transform.XSDValidator;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;

public class SE01Route extends Route {

    private final static String INPUT_XSD = "rest/src/main/resources/SE01/SE01_Input.xsd";
    private final static String OUTPUT_XSD = "rest/src/main/resources/SE01/SE01_Output.xsd";
    private final static String OUTPUT_XHTML_XSL = "rest/src/main/resources/SE01/SE01_Output_XHTML.xsl";
    private final static String OUTPUT_JSON_XSL = "rest/src/main/resources/SE01/SE01_Output_JSON.xsl";

    public SE01Route(HTTPMessage request, HTTPMessage response) throws IOException {
        super(request, response);
    }

    public void submeterPedidoAvaliacao() {
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
            response.setContentFromString(formatUtils.buildOutputString("ERROR validating input XML against XSD", getXSLErrorFileName()), Settings.xmlOutputFormat);
            return;
        }

        // parametro de validacao é passo na xmlInputString e internamente será colocado no respetivo estado após avaliar
        String message = new ProcessCasoFromXMLController().processarCaso(xmlInputString);
        SE01Output output = null;
        if (message == null) {
            response.setResponseStatus(Settings.STATUS_BAD_REQUEST);
            response.setContentFromString(formatUtils.buildOutputString("ERROR evaluating caso", getXSLErrorFileName()), Settings.xmlOutputFormat);
            return;
        } else {
            output = new SE01Output(message);
        }

        String xmlOutputString = buildXMLString(output);

        // validate output in XML
        try {
            new XSDValidator(xmlOutputString, OUTPUT_XSD).validateXMLwithXSD();
        } catch (IOException e) {
            e.printStackTrace();
            response.setResponseStatus(Settings.STATUS_BAD_REQUEST);
            response.setContentFromString(formatUtils.buildOutputString("ERROR validating raconsole.output XML against XSD", getXSLErrorFileName()), Settings.xmlOutputFormat);
            return;
        }

        // build final response
        response.setResponseStatus(Settings.STATUS_OK);
        String outputString = formatUtils.buildOutputString(xmlOutputString, getXSLFileName());
        response.setContentFromString(outputString, outputFormat);
    }

    public String buildXMLString(SE01Output o) {
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
