package rest.utils;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import rest.Settings;
import utils.validate_transform.XSLTransform;

import java.io.IOException;
import java.io.StringWriter;

public class FormatUtils {

    public String defineOutputFormat(String parameters) throws IOException {
        if (parameters != null) {
            if (parameters.toLowerCase().contains("xhtml".toLowerCase())) {
                return Settings.xhtmlOutputFormat;
            } else if (parameters.toLowerCase().contains("xml".toLowerCase())) {
                return Settings.xmlOutputFormat;
            } else if (!parameters.toLowerCase().contains("json".toLowerCase())) {
                throw new IOException();
            }
        }
        return Settings.jsonOutputFormat;
    }

    private String xmlMinify(final String xml) {
        final StringWriter sw;
        try {
            final OutputFormat format = OutputFormat.createCompactFormat();
            final org.dom4j.Document document = DocumentHelper.parseText(xml);
            sw = new StringWriter();
            final XMLWriter writer = new XMLWriter(sw, format);
            writer.write(document);
        } catch (Exception e) {
            System.err.println("Error un-pretty printing xml:\n" + xml);
            e.printStackTrace();
            return null;
        }
        return sw.toString();
    }

    public String buildXMLInputString(String str, String format) {
        if (str != null && format != null) {
            if (format.toLowerCase().contains("xml".toLowerCase())) {
                return xmlMinify(str);
            } else if (format.toLowerCase().contains("json".toLowerCase())) {
                try {
                    JSONObject json = new JSONObject(str);
                    return xmlMinify(XML.toString(json));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    public String buildOutputString(String xmlString, String xslString) {
        if (xslString == null) {
            return buildXMLOutputString(xmlString);
        }

        XSLTransform xslTransform;
        try {
            xslTransform = new XSLTransform(buildXMLOutputString(xmlString), xslString);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return xslTransform.transformXMLwithXSL();
    }

    public String buildXMLOutputString(String str) {
        String XMLHeader = "xml version=\"1.0\" encoding=\"UTF-8\"";
        if (!str.contains(XMLHeader)) {
            return String.format("<?%s?><error>%s</error>", XMLHeader, str);
        }
        return str;
    }

}
