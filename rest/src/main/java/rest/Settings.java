package rest;

public class Settings {

    public final static String ERROR_OUTPUT_JSON_XSL = "rest/src/main/resources/Error_Output_JSON.xsl";
    public final static String ERROR_OUTPUT_XHTML_XSL = "rest/src/main/resources/Error_Output_XHTML.xsl";
    public static int PORT = 8080;
    public static String STATUS_OK = "200 OK";
    public static String STATUS_BAD_REQUEST = "400 Bad Request";
    public static String STATUS_NOT_FOUND = "404 Not Found";
    public static String STATUS_NOT_ALLOWED = "405 Method Not Allowed";
    public static String jsonOutputFormat = "application/json";
    public static String xhtmlOutputFormat = "application/xhtml+xml";
    public static String xmlOutputFormat = "application/xml";

}
