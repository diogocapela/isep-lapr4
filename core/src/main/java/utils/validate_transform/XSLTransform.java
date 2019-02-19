package utils.validate_transform;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class XSLTransform {

    private static final Object syncXSLObject = new Object();
    private static int IDsequence = 0;
    private String xmlString;
    private File xmlFile;
    private File xslFile;
    private int id;

    public XSLTransform(String xmlString, String xslFileName) throws IOException {
        synchronized (syncXSLObject) {
            this.id = IDsequence++;
        }
        this.xmlString = xmlString;
        this.xslFile = new File(new java.io.File(".").getCanonicalPath() + "/" + xslFileName);
    }

    public String transformXMLwithXSL() {
        FileUtils fileUtils = new FileUtils();
        // XML FILE
        xmlFile = fileUtils.createFile(xmlString, String.format("xml_xsl_%d.xml", id));
        if (xmlFile == null) {
            return null;
        }

        StringWriter output = new StringWriter();
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer =
                factory.newTransformer(new StreamSource(xslFile));
            StreamSource xmlsource = new StreamSource(xmlFile);
            transformer.transform(xmlsource, new StreamResult(output));
        } catch (TransformerException e) {
            e.printStackTrace();
            xmlFile.delete();
            return null;
        }

        if (!xmlFile.delete()) {
            return null;
        }

        return output.toString();
    }
}
