package georef.strategies.sg01;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import georef.Settings;
import georef.models.Location;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SG01Google {
    public Location getLocationFromPostalCode(String postalCode) {
        if (postalCode == null) throw new IllegalArgumentException();
        try {
            // Config API
            GeoApiContext context = new GeoApiContext.Builder().apiKey(Settings.GOOGLE_API_KEY).build();
            // Get results from postal code parameter
            GeocodingResult[] results = GeocodingApi.geocode(context, postalCode).await();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResponse = gson.toJson(results);

            // Parse the results from JSON to POJO
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonResponse);

            if (jsonArray.size() == 0) return null;

            JSONObject jsonObject = (JSONObject) jsonArray.get(0);

            JSONObject geometryJSON = (JSONObject) jsonObject.get("geometry");
            JSONObject locationJSON = (JSONObject) geometryJSON.get("location");

            Double lat = (Double) locationJSON.get("lat");
            Double lng = (Double) locationJSON.get("lng");

            Location location = new Location();

            location.setLatitude(lat);
            location.setLongitude(lng);
            location.setPostalCode(postalCode);

            return location;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
