package utils.validate_transform;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XSDValidator {

    private static final Object syncXSDObject = new Object();
    private static int IDsequence = 0;
    private String xmlString;
    private File xmlFile;
    private File xsdFile;
    private int id;

    public XSDValidator(String xmlString, String xsdFileName) throws IOException {
        synchronized (syncXSDObject) {
            this.id = IDsequence++;
        }
        this.xmlString = xmlString;
        this.xsdFile = new File(new java.io.File(".").getCanonicalPath() + "/" + xsdFileName);
    }

    public boolean validateXMLwithXSD() {
        FileUtils fileUtils = new FileUtils();

        xmlFile = fileUtils.createFile(xmlString, String.format("xml_xsd_%d.xml", id));
        if (xmlFile == null) {
            return false;
        }

        //validate XML with XSD
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(xsdFile);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
            xmlFile.delete();
            return false;
        }

        try {
            xmlFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
