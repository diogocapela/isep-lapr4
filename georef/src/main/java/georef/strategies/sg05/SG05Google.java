package georef.strategies.sg05;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import georef.Settings;
import georef.models.Location;
import georef.models.Route;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SG05Google {
    private final String WALKING_ = "Walking";
    private final String DRIVING_ = "Driving";

    ////////////////////////////////// WITH ADDRESS

    public Route getRouteBetweenLocations(String addressA, String addressB, String locomocao) {
        // Validate input
        TravelMode mode;
        if (addressA == null || addressB == null || addressA.isEmpty() || addressB.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            addressA = buildAddressURL(addressA);
            addressB = buildAddressURL(addressB);
        }

        if (locomocao == null || locomocao.isEmpty()) {
            mode = TravelMode.DRIVING;
        } else if (locomocao.equalsIgnoreCase(WALKING_)) {
            mode = TravelMode.WALKING;
        } else {
            mode = TravelMode.DRIVING;
        }

        try {
            // Config API
            GeoApiContext context = new GeoApiContext.Builder().disableRetries().apiKey(Settings.GOOGLE_API_KEY).build();
            DirectionsResult result;
            try {
                result = DirectionsApi.newRequest(context).origin(addressA).destination(addressB).mode(mode).await();
                Route route = getResult(result, locomocao);

                return route;

            } catch (NullPointerException ne) {
                ne.printStackTrace();
                return null;
            } catch (ApiException apie) {
                apie.getCause();
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String buildAddressURL(String address) {
        return address.replaceAll("[ ]", "%20");
    }

    /////////////////////////////////// WITH COORDINATES

    public Route getRouteBetweenLocations(Double latA, Double longA, Double latB, Double longB, String locomocao) {

        // Validate input
        TravelMode mode;
        LatLng latLongA;
        LatLng latLongB;
        if (latA == null || longA == null || latB == null || longB == null) {
            throw new IllegalArgumentException();
        } else {
            latLongA = new LatLng(latA, longA);
            latLongB = new LatLng(latB, longB);
        }

        if (locomocao == null || locomocao.isEmpty()) {
            mode = TravelMode.DRIVING;
        } else if (locomocao.equalsIgnoreCase(WALKING_)) {
            mode = TravelMode.WALKING;
        } else {
            mode = TravelMode.DRIVING;
        }

        try {
            // Config API
            GeoApiContext context = new GeoApiContext.Builder().disableRetries().apiKey(Settings.GOOGLE_API_KEY).build();
            DirectionsResult result;
            try {
                result = DirectionsApi.newRequest(context).origin(latLongA).destination(latLongB).mode(mode).await();

                Route route = getResult(result, locomocao);

                return route;
            } catch (NullPointerException ne) {
                ne.printStackTrace();
                return null;
            } catch (ApiException apie) {
                System.out.println(apie.getMessage());
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Route getResult(DirectionsResult result, String locomocao) {

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResponse = gson.toJson(result);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonResponse);
            if (jsonObj == null) return null;

            JSONArray routes = (JSONArray) jsonObj.get("routes");
            if (routes.size() == 0) return null;

            JSONObject infosBag = (JSONObject) routes.get(0);
            if (infosBag == null) return null;

            JSONArray legs = (JSONArray) infosBag.get("legs");
            if (legs.size() == 0) return null;

            JSONObject finalBag = (JSONObject) legs.get(0);
            if (finalBag == null) return null;

            // Time given in seconds
            JSONObject timeBag = (JSONObject) finalBag.get("duration");
            if (timeBag == null) return null;
            Double time = Double.parseDouble(timeBag.get("inSeconds").toString());

            // Distance given in meters -> dividir por 1000 para passar para km
            JSONObject distanceBag = (JSONObject) finalBag.get("distance");
            if (distanceBag == null) return null;
            Double distance = Double.parseDouble(distanceBag.get("inMeters").toString());

            // Locationn A
            JSONObject startLocation = (JSONObject) finalBag.get("startLocation");
            if (startLocation == null) return null;
            Double latitudeA = Double.parseDouble(startLocation.get("lat").toString());
            Double longitudeA = Double.parseDouble(startLocation.get("lng").toString());

            // Location B
            JSONObject endLocation = (JSONObject) finalBag.get("endLocation");
            if (endLocation == null) return null;
            Double latitudeB = Double.parseDouble(endLocation.get("lat").toString());
            Double longitudeB = Double.parseDouble(endLocation.get("lng").toString());

            // Route data
            Location locationA = new Location(latitudeA, longitudeA);
            Location locationB = new Location(latitudeB, longitudeB);
            Route route = new Route(locationA, locationB, distance / 1000, time, locomocao);

            return route;
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }

}
