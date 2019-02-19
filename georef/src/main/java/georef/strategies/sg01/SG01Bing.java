package georef.strategies.sg01;

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

public class SG01Bing {

    public Location getLocationFromPostalCode(String postalCode) {
        // Validate input
        if (postalCode == null) throw new IllegalArgumentException();
        // Curate the input data
        String curetedLocality = extractLocality(postalCode);
        String curatedPostalCode = extractPostalCode(postalCode);
        try {
            // Build the endpoint
            URL url = buildURLEndpoint(curetedLocality, curatedPostalCode);
            // Open the connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Get the input stream data
            InputStream inputStream = connection.getInputStream();
            // Parse the JSON
            JSONArray coordinates = getJSONCoordinatesFromInputStream(inputStream);
            // Validate result
            if (coordinates == null || coordinates.size() < 2) return null;
            // Extract data
            Double lat = (Double) coordinates.get(0);
            Double lng = (Double) coordinates.get(1);
            // Build object and deliver
            return new Location(lat, lng);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String extractPostalCode(String address) {
        return address.replaceAll("[^0123456789-]", "");
    }

    private String extractLocality(String address) {
        return address.replaceAll("\\d|-", "").trim();
    }

    private URL buildURLEndpoint(String locality, String postalCode) throws IOException {
        String endpoint = "http://dev.virtualearth.net/REST/v1/Locations?key=" + Settings.MICROSOFT_API_KEY;
        if (postalCode.length() > 0) {
            endpoint = endpoint + "&postalCode=" + URLEncoder.encode(postalCode, "UTF-8");
        }
        if (locality.length() > 0) {
            endpoint = endpoint + "&locality=" + URLEncoder.encode(locality, "UTF-8");
        }
        return new URL(endpoint);
    }

    private JSONArray getJSONCoordinatesFromInputStream(InputStream inputStream) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(inputStreamReader);

        JSONArray resourceSets = (JSONArray) jsonObject.get("resourceSets");
        if (resourceSets.size() == 0) return null;

        JSONObject firstResourceSet = (JSONObject) resourceSets.get(0);
        JSONArray resources = (JSONArray) firstResourceSet.get("resources");
        if (resources.size() == 0) return null;

        JSONObject firstResource = (JSONObject) resources.get(0);
        JSONObject point = (JSONObject) firstResource.get("point");
        JSONArray coordinates = (JSONArray) point.get("coordinates");
        if (coordinates.size() < 2) return null;

        return coordinates;
    }

}
