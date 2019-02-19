package rest;

import java.io.*;

public class HTTPMessage {

    // Static Elements
    //==================================================================================

    private static final int CR = 13;
    private static final int LF = 10;

    private static final String VERSION = "HTTP/1.1";

    private static final String CONTENT_TYPE = "Content-type:";
    private static final String CONTENT_LENGTH = "Content-length:";
    private static final String CONNECTION = "Connection:";
    private static final String TOKEN_KEY = "TokenKey";

    private static final String[][] knownFileExtensions = {
        {".pdf", "application/pdf"},
        {".js", "application/javascript"},
        {".json", "application/json"},
        {".txt", "text/plain"},
        {".html", "text/html"},
        {".xhtml", "application/xhtml+xml"},
        {".xml", "application/xml"},
        {".gif", "image/gif"},
        {".png", "image/png"},
    };
    private boolean isRequest;
    private String method;

    // Non-static Elements
    //==================================================================================
    private String uri;
    private String status;
    private String parameters;
    private String parametersGet;
    private String contentType;
    private byte[] content;
    private String TokenKey;

    /**
     * Creates a new rest.rest.HTTPMessage request by receiving it from a DataInputStream.
     */
    public HTTPMessage(DataInputStream dataInputStream) throws IOException {
        String firstLine = readHeaderLine(dataInputStream);
        isRequest = !firstLine.startsWith("HTTP/");
        method = "";
        uri = "";
        content = null;
        status = null;
        parameters = null;
        parametersGet = null;
        contentType = null;
        TokenKey = null;

        String[] firstLineComp = firstLine.split(" ");

        if (isRequest && firstLineComp.length > 1) {
            method = firstLineComp[0];
            try {
                extractParameters(firstLineComp[1]);
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
            }
        } else if (firstLineComp.length > 2) {
            status = firstLineComp[1] + " " + firstLineComp[2];
        }

        String headerLine;

        do {
            headerLine = readHeaderLine(dataInputStream);

            if (headerLine.toUpperCase().startsWith(CONTENT_TYPE.toUpperCase())) {
                contentType = headerLine.substring(CONTENT_TYPE.length()).trim();
            } else if (headerLine.toUpperCase().startsWith(CONTENT_LENGTH.toUpperCase())) {
                String contentLength = headerLine.substring(CONTENT_LENGTH.length()).trim();
                int len = Integer.parseInt(contentLength);
                content = new byte[len];
            } else if (headerLine.toUpperCase().startsWith(TOKEN_KEY.toUpperCase().trim())) {
                String[] s = headerLine.split(":");
                if (s.length == 2) {
                    TokenKey = s[1].trim();
                }
            }
        } while (!headerLine.isEmpty());

        // Read Content
        if (content != null) {
            dataInputStream.readFully(content, 0, content.length);
        }
    }

    /**
     * Creates a new rest.rest.HTTPMessage response.
     */
    public HTTPMessage() {
        isRequest = true;
        method = null;
        uri = null;
        status = null;
        content = null;
        contentType = null;
        parameters = null;
    }

    // Constructors
    //==================================================================================

    private static String readHeaderLine(DataInputStream dataInputStream) throws IOException {
        String ret = "";
        int val;
        do {
            val = dataInputStream.read();
            if (val == -1) break;
            if (val != CR) ret = ret + (char) val;
        } while (val != CR);
        dataInputStream.read(); // read LF
        //System.out.println(ret);
        return ret;
    }

    private static void writeHeaderLine(DataOutputStream dataOutputStream, String line) throws IOException {
        if (dataOutputStream != null && line != null) {
            dataOutputStream.write(line.getBytes(), 0, line.length());
            dataOutputStream.write(CR);
            dataOutputStream.write(LF);
        }
    }

    // Getters
    //==================================================================================

    public String getMethod() {
        return method;
    }

    public String getURI() {
        return uri;
    }

    public void setURI(String uri) {
        this.uri = uri;
    }

    public String getParameters() {
        return parameters;
    }

    public String getStatus() {
        return status;
    }

    public byte[] getContent() {
        return (content);
    }

    public String getParametersGet() {
        return this.parametersGet;
    }

    public String getContentAsString() {
        if (this.content != null)
            return (new String(content));
        else
            return "";
    }

    public String getToken() {
        if (this.TokenKey != null)
            return this.TokenKey;
        else
            return "";
    }

    // Setters
    //==================================================================================

    public String getContentType() {
        return contentType;
    }

    public void setRequestMethod(String method) {
        isRequest = true;
        this.method = method;
    }

    public void setResponseStatus(String status) {
        isRequest = false;
        this.status = status;
    }

    public void setContentFromString(String content, String contentType) {
        this.content = content.getBytes();
        this.contentType = contentType;
    }

    public boolean setContentFromFile(String filePath) {
        File file = new File(filePath);
        contentType = null;

        if (!file.exists()) {
            content = null;
            return false;
        }

        for (String[] fileExtension : knownFileExtensions) {
            if (filePath.endsWith(fileExtension[0])) {
                contentType = fileExtension[1];
            }
        }

        if (contentType == null) {
            contentType = "text/html";
        }

        int contentLength = (int) file.length();

        if (contentLength == 0) {
            content = null;
            contentType = null;
            return false;
        }

        content = new byte[contentLength];

        DataInputStream dataInputStream;

        try {
            dataInputStream = new DataInputStream(new FileInputStream(file));
            try {
                dataInputStream.readFully(content, 0, contentLength);
                dataInputStream.close();
            } catch (IOException ex) {
                System.out.println("Error Reading File");
                content = null;
                contentType = null;
                return false;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
            content = null;
            contentType = null;
            return false;
        }
        return true;
    }

    // Custom Methods
    //==================================================================================

    public boolean send(DataOutputStream dataOutputStream) throws IOException {
        if (isRequest) {
            if (method == null || uri == null) return false;
            writeHeaderLine(dataOutputStream, method + " " + uri + " " + VERSION);
        } else {
            if (status == null) return false;
            writeHeaderLine(dataOutputStream, VERSION + " " + status);
        }

        if (content != null) {
            if (contentType != null) {
                writeHeaderLine(dataOutputStream, CONTENT_TYPE + " " + contentType);
            }
            writeHeaderLine(dataOutputStream, CONTENT_LENGTH + " " + content.length);
        }
        writeHeaderLine(dataOutputStream, CONNECTION + " close");
        writeHeaderLine(dataOutputStream, "");
        if (content != null) {
            dataOutputStream.write(content, 0, content.length);
        }
        return true;
    }

    public boolean hasContent() {
        return (content != null);
    }

    private void extractParameters(String notMethod) throws IOException {
        String parts[] = notMethod.split("[?]", 2);
        if (parts.length > 1) {
            uri = parts[0];
            parameters = parts[1];
            if (parameters.split("&").length > 1) {
                throw new IOException();
            }
        } else {
            uri = notMethod;
        }

    }

}
