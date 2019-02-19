package rest.routes;

import rest.HTTPMessage;
import rest.HTTPServer;

public class SEAuthRoute {
    //TODO: This is the class of the login route.

    /**
     * This class provides a token handler to the http client.
     * This shall be called when login rest call is invoked.
     * If a user logins and has already a valid token, this one will
     * replace the older one.
     */

    public SEAuthRoute(HTTPServer server, HTTPMessage request, HTTPMessage response) {
    }
}
