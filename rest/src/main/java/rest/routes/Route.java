package rest.routes;

import rest.HTTPMessage;
import rest.Settings;
import rest.utils.FormatUtils;

import java.io.IOException;

public abstract class Route {

    public HTTPMessage request;
    public HTTPMessage response;
    public FormatUtils formatUtils = new FormatUtils();
    public String outputFormat;

    public Route(HTTPMessage request, HTTPMessage response) throws IOException {
        this.request = request;
        this.response = response;
        this.outputFormat = formatUtils.defineOutputFormat(request.getParameters());
    }

    public String getXSLErrorFileName() {
        if (outputFormat.equals(Settings.xmlOutputFormat)) {
            return null;
        }
        if (outputFormat.equals(Settings.xhtmlOutputFormat)) {
            return Settings.ERROR_OUTPUT_XHTML_XSL;
        } else if (outputFormat.equals(Settings.jsonOutputFormat)) {
            return Settings.ERROR_OUTPUT_JSON_XSL;
        }
        return null;
    }

}
