package georef.strategies.sg05;

import georef.Settings;
import georef.models.Location;
import georef.models.Route;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SG05Bing {

    private final String WALKING = "Walking";
    private final String DRIVING = "Driving";

    ////////////////////////////////// WITH ADDRESS

    public Route getRouteBetweenLocations(String addressA, String addressB, String locomocao) {

        // Validate input
        if (addressA == null || addressB == null || addressA.isEmpty() || addressB.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            addressA = buildAddressURL(addressA);
            addressB = buildAddressURL(addressB);
        }

        if (locomocao == null) {
            locomocao = DRIVING;
        } else if (locomocao.equalsIgnoreCase(WALKING)) {
            locomocao = WALKING;
        } else {
            locomocao = DRIVING;
        }

        try {
            // Build the endpoint
            URL url = buildURLEndpoint(addressA, addressB, locomocao);
            // Open the connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Get the input stream data
            InputStream inputStream = connection.getInputStream();

            Route route = getRouteFromInputStream(inputStream, locomocao);

            return route;
        } catch (Exception e) {
            e.getCause();
            return null;
        }
    }

    private String buildAddressURL(String address) {
        return address.replaceAll("[ ]", "%20");
    }

    private URL buildURLEndpoint(String addressA, String addressB, String locomocao) throws IOException {
        String endpoint = "http://dev.virtualearth.net/REST/v1/Routes/" + locomocao +
            "?wp.1=" + addressA + "&wp.2=" + addressB +
            "&key=" + Settings.MICROSOFT_API_KEY;

        return new URL(endpoint);
    }

    private Route getRouteFromInputStream(InputStream inputStream, String locomocao) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(inputStreamReader);

        JSONArray resourceSets = (JSONArray) jsonObject.get("resourceSets");
        if (resourceSets.size() == 0) return null;
        JSONObject resourcesBag = (JSONObject) resourceSets.get(0);
        if (resourcesBag == null) return null;
        ;

        JSONArray resources = (JSONArray) resourcesBag.get("resources");
        if (resources.size() == 0) return null;

        JSONObject routeLegsBag = (JSONObject) resources.get(0);
        if (routeLegsBag == null) return null;

        JSONArray routeLegs = (JSONArray) routeLegsBag.get("routeLegs");
        if (routeLegs.size() == 0) return null;

        //              Locations
        JSONObject locationsBag = (JSONObject) routeLegs.get(0);
        if (locationsBag == null) return null;

        // Location A
        JSONObject startLocation = (JSONObject) locationsBag.get("startLocation");
        if (startLocation == null) return null;

        JSONObject pointA = (JSONObject) startLocation.get("point");
        if (pointA == null) return null;

        JSONArray coordinatesA = (JSONArray) pointA.get("coordinates");
        if (coordinatesA.size() == 0) return null;

        Double latitudeA = Double.parseDouble(coordinatesA.get(0).toString());
        Double longitudeA = Double.parseDouble(coordinatesA.get(1).toString());

        // Location B
        JSONObject endLocation = (JSONObject) locationsBag.get("endLocation");
        if (endLocation == null) return null;

        JSONObject pointB = (JSONObject) endLocation.get("point");
        if (pointB == null) return null;

        JSONArray coordinatesB = (JSONArray) pointB.get("coordinates");
        if (coordinatesB.size() == 0) return null;

        Double latitudeB = Double.parseDouble(coordinatesB.get(0).toString());
        Double longitudeB = Double.parseDouble(coordinatesB.get(1).toString());

        //Duration in seconds
        Double travelDuration = Double.parseDouble(routeLegsBag.get("travelDuration").toString());
        if (travelDuration == null) return null;

        //Distance in km
        Double travelDistance = Double.parseDouble(routeLegsBag.get("travelDistance").toString());
        if (travelDistance == null) return null;

        //Route data
        Location locationA = new Location(latitudeA, longitudeA);
        Location locationB = new Location(latitudeB, longitudeB);
        Route route = new Route(locationA, locationB, travelDistance, travelDuration, locomocao);

        return route;
    }

    /////////////////////////////////// WITH COORDINATES

    public Route getRouteBetweenLocations(Double latA, Double longA, Double latB, Double longB, String locomocao) {

        // Validate input
        if (latA == null || longA == null || latB == null || longB == null) {
            throw new IllegalArgumentException();
        }
        if (locomocao.equalsIgnoreCase(WALKING)) {
            locomocao = WALKING;
        } else {
            locomocao = DRIVING;
        }

        try {
            // Build the endpoint
            URL url = buildURLEndpointCoordinates(latA, longA, latB, longB, locomocao);

            // Open the connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Get the input stream data
            InputStream inputStream = connection.getInputStream();

            Route route = getRouteFromInputStreamCoordinates(inputStream, latA, longA, latB, longB, locomocao);

            return route;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private URL buildURLEndpointCoordinates(Double latA, Double longA, Double latB, Double longB, String locomocao) throws IOException {
        String endpoint = "http://dev.virtualearth.net/REST/v1/Routes/" + locomocao +
            "?wp.1=" + latA.toString() + "," + longA.toString() + "&wp.2=" + latB.toString() + "," + longB.toString() +
            "&key=" + Settings.MICROSOFT_API_KEY;

        return new URL(endpoint);
    }

    private Route getRouteFromInputStreamCoordinates(InputStream inputStream, Double latA, Double longA, Double latB, Double longB, String locomocao) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(inputStreamReader);

        JSONArray resourceSets = (JSONArray) jsonObject.get("resourceSets");
        if (resourceSets.size() == 0) return null;

        JSONObject resourcesBag = (JSONObject) resourceSets.get(0);
        if (resourcesBag == null) return null;
        ;

        JSONArray resources = (JSONArray) resourcesBag.get("resources");
        if (resources == null) return null;
        ;

        JSONObject infosBag = (JSONObject) resources.get(0);
        if (infosBag == null) return null;
        ;

        //Duration in seconds
        Double travelDuration = Double.parseDouble(infosBag.get("travelDuration").toString());
        if (travelDuration == null) return null;

        //Distance in km
        Double travelDistance = Double.parseDouble(infosBag.get("travelDistance").toString());
        if (travelDistance == null) return null;

        //Route data
        Location locationA = new Location(latA, longA);
        Location locationB = new Location(latB, longB);
        Route route = new Route(locationA, locationB, travelDistance, travelDuration, locomocao);

        return route;
    }
}
