package georef.strategies.sg04;

import georef.Settings;
import georef.models.Location;
import georef.strategies.sg01.SG01Bing;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class SG04Bing {

    private Location local;

    public Location seaLevelLocation(String postalCode) {
        local = new SG01Bing().getLocationFromPostalCode(postalCode);
        double lat = local.getLatitude();
        double lon = local.getLongitude();
        URLBing(lat, lon);
        return local;
    }

    public void URLBing(double x, double y) {
        try {
            URL url = buildURLEndpoint(x, y);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Get the input stream data
            InputStream inputStream = connection.getInputStream();
            // Parse the JSON
            JSONArray elevation = getJSONCoordinatesFromInputStream(inputStream);
            String s = elevation.toString();
            int tam = s.length();
            String elev = s.substring(1, tam - 1);
            Double alt = Double.parseDouble(elev);
            local.setAltitude(alt);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private URL buildURLEndpoint(double x, double y) throws IOException {
        String s = x + "," + y;
        String endpoint = "http://dev.virtualearth.net/REST/v1/Elevation/List?points=" + s + "&key=" + Settings.MICROSOFT_API_KEY;
        endpoint = endpoint + "&s=" + URLEncoder.encode(s, "UTF-8");
        return new URL(endpoint);
    }

    private JSONArray getJSONCoordinatesFromInputStream(InputStream inputStream) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(inputStreamReader);

        JSONArray resourceSets = (JSONArray) jsonObject.get("resourceSets");
        JSONObject firstResourceSet = (JSONObject) resourceSets.get(0);

        JSONArray firstResourceSet1 = (JSONArray) firstResourceSet.get("resources");
        JSONObject resources = (JSONObject) firstResourceSet1.get(0);

        JSONArray elevation = (JSONArray) resources.get("elevations");
        return elevation;
    }

}







