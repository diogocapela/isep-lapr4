package georef.strategies.sg02;

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

public class SG02Google {

    public SG02Google() {

    }

    public Location enrichPostalLocation(String postalAddress) {
        if (postalAddress == null) throw new IllegalArgumentException();
        try {
            // Config API
            GeoApiContext context = new GeoApiContext.Builder().apiKey(Settings.GOOGLE_API_KEY).build();

            // Get results from postal code parameter
            GeocodingResult[] results = GeocodingApi.geocode(context, postalAddress).await();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResponse = gson.toJson(results);
            //System.out.println(jsonResponse);

            // Parse the results from JSON to POJO
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonResponse);
            if (jsonArray.size() == 0) {
                return null;
            }

            JSONObject jsonObject = (JSONObject) jsonArray.get(0);

            JSONArray addressComponentsJSON = (JSONArray) jsonObject.get("addressComponents");

            JSONObject postalCodeJSON = new JSONObject();
            JSONObject cityJSON = new JSONObject();
            JSONObject countryJSON = new JSONObject();
            JSONObject districtJSON = new JSONObject();

            for (int i = 0; i < addressComponentsJSON.size(); i++) {
                JSONObject jsonObjectTemp = (JSONObject) addressComponentsJSON.get(i);
                JSONArray jsonArrayTemp = (JSONArray) jsonObjectTemp.get("types");
                if (jsonArrayTemp.contains("POSTAL_CODE")) {
                    postalCodeJSON = (JSONObject) addressComponentsJSON.get(i);
                }
                if (jsonArrayTemp.contains("LOCALITY")) {
                    cityJSON = (JSONObject) addressComponentsJSON.get(i);
                }
                if (jsonArrayTemp.contains("ADMINISTRATIVE_AREA_LEVEL_1")) {
                    districtJSON = (JSONObject) addressComponentsJSON.get(i);
                }
                if (jsonArrayTemp.contains("COUNTRY")) {
                    countryJSON = (JSONObject) addressComponentsJSON.get(i);
                }
            }
            if (!postalCodeJSON.isEmpty() && !cityJSON.isEmpty() && !districtJSON.isEmpty() && !countryJSON.isEmpty()) {
                String postalCode = (String) postalCodeJSON.get("longName");
                String city = (String) cityJSON.get("longName");
                String district = (String) districtJSON.get("longName");
                String country = (String) countryJSON.get("longName");
                Location location = new Location();
                location.setPostalCode(postalCode);
                location.setCity(city);
                location.setDistrict(district);
                location.setCountry(country);
                return location;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

