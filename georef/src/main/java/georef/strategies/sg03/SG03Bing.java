package georef.strategies.sg03;

import georef.Settings;
import georef.models.Location;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;

public class SG03Bing {

    private Integer radius;

    public LinkedList<Location> getLocationsByType(String envolvente, Integer radiusMeters, Location referenceLocation) {

        if (radiusMeters == null || radiusMeters == 0) {
            this.radius = 10;
        } else {
            int radiusKM = radiusMeters / 1000;
            this.radius = radiusKM == 0 ? 1 : radiusKM;
        }
        LinkedList<Location> locations = new LinkedList<>();

        try {
            String l_url = buildURLEndpoint(referenceLocation.getLatitude(), referenceLocation.getLongitude(), this.radius, envolvente);
            JSONArray returned_data = getDataFromLocationsBingURL(l_url);

            for (int i = 0; i < returned_data.size(); i++) {
                //get the first json object of the index.
                JSONObject jobj = (JSONObject) returned_data.get(i);

                String jobj_name = (String) jobj.get("name");

                JSONArray jobj_cord = ((JSONArray) ((JSONObject) jobj.get("point")).get("coordinates"));

                String jobj_lat = jobj_cord.get(0).toString();

                String jobj_lng = jobj_cord.get(1).toString();

                String adminDestrict = (String) ((JSONObject) jobj.get("Address")).get("adminDistrict");

                String countryRegion = (String) ((JSONObject) jobj.get("Address")).get("countryRegion");

                String locality = (String) ((JSONObject) jobj.get("Address")).get("locality");

                String postalCode = (String) ((JSONObject) jobj.get("Address")).get("postalCode");

                String street = (String) ((JSONObject) jobj.get("Address")).get("addressLine");

                Location l = new Location(Double.parseDouble(jobj_lat), Double.parseDouble(jobj_lng));

                l.setTitle(jobj_name);
                l.setStreet(street);
                l.setDistrict(adminDestrict);
                l.setCountry(countryRegion);
                l.setCity(locality);
                l.setPostalCode(postalCode);
                locations.add(l);
            }

        } catch (Exception ex) {
            return null;
        }


        return locations;
    }

    private JSONArray getDataFromLocationsBingURL(String endpoint) {
        try {
            URL url = new URL(endpoint);
            // Open the connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Receive the JSON response body
            InputStream inputStream = connection.getInputStream();
            // Parse the JSON
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));
            //PrettyPrintJSON.prettyPrint(jsonObject.toJSONString());
            JSONArray resourceSets = (JSONArray) jsonObject.get("resourceSets");
            if (resourceSets.size() == 0) {
                return null;
            }
            JSONObject firstResourceSets = (JSONObject) resourceSets.get(0);
            JSONArray resources = (JSONArray) firstResourceSets.get("resources");

            if (resources.size() == 0) {
                return null;
            }
            return resources;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String buildURLEndpoint(Double lat, Double lng, Integer radius, String envolvente) throws IOException {
        String endpoint = "https://dev.virtualearth.net/REST/v1/LocalSearch?key=" + Settings.MICROSOFT_API_KEY;
        endpoint = endpoint + "&query=" + URLEncoder.encode(envolvente, "UTF-8");
        endpoint = endpoint + "&userLocation=" + URLEncoder.encode(lat + "," + lng + "," + radius, "UTF-8");
        return endpoint;
    }
}
