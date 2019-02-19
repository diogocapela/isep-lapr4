package georef.strategies.sg04;

import georef.Settings;
import georef.models.Location;
import georef.strategies.sg01.SG01Google;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SG04Google {

    private Location local;

    public Location seaLevelLocation(String postalCode) {

        local = new SG01Google().getLocationFromPostalCode(postalCode);
        double lat = local.getLatitude();
        double lon = local.getLongitude();
        URLGoogle(lat, lon);
        return local;
    }

    private void URLGoogle(double x, double y) {
        try {

            URL url = buildURLEndpoint(x, y);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Get the input stream data
            InputStream inputStream = connection.getInputStream();
            // Parse the JSON
            Double elevation = getJSONCoordinatesFromInputStream(inputStream);

            Double alt = (double) elevation.intValue();
            local.setAltitude(alt);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private URL buildURLEndpoint(double x, double y) throws IOException {
        String endpoint = "https://maps.googleapis.com/maps/api/elevation/json?locations=" + x + "," + y + "&key=" + Settings.GOOGLE_API_KEY;
        return new URL(endpoint);
    }

    private double getJSONCoordinatesFromInputStream(InputStream inputStream) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(inputStreamReader);

        JSONArray resourceSets = (JSONArray) jsonObject.get("results");

        JSONObject firstResourceSet = (JSONObject) resourceSets.get(0);

        return (Double) firstResourceSet.get("elevation");
    }
}
