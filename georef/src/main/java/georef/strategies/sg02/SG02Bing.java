package georef.strategies.sg02;

import georef.Settings;
import georef.models.Location;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SG02Bing {

    public SG02Bing() {

    }


    public Location enrichPostalLocation(String addressCode) {
        if (addressCode == null) throw new IllegalArgumentException();
        try {
            String curatedPostalCode = curatePostalCode(addressCode);
            if (curatedPostalCode.equals("")) {
                return null;
            }
            String curatedLocality = curateLocality(addressCode);
            String endpoint = buildBingLocationsEndPoint(curatedLocality, curatedPostalCode);
            // Construct the URL with the built string
            JSONObject data = getDataFromLocationsBingURL(endpoint);
            if (data == null || data.isEmpty()) {
                return null;
            }
            // Locality Data
            JSONObject addressData = (JSONObject) data.get("address");
            String title = (String) data.get("name");
            String country = (String) addressData.get("countryRegion");
            String district = (String) addressData.get("adminDistrict");
            if (district == null) {
                district = (String) addressData.get("adminDistrict2");
            }
            String city = (String) addressData.get("locality");
            // Create Location Object
            Location location = new Location();
            location.setTitle(title);
            location.setCountry(country);
            location.setDistrict(district);
            location.setCity(city);
            location.setPostalCode(curatedPostalCode);
            return location;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject getDataFromLocationsBingURL(String endpoint) {
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
            // georef.ui.Main Data is HERE:
            JSONObject data = (JSONObject) resources.get(0);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String buildBingLocationsEndPoint(String curatedLocality, String curatedPostalCode) {
        try {
            // Endpoint to build upon
            String endpoint = "http://dev.virtualearth.net/REST/v1/Locations?key=" + Settings.MICROSOFT_API_KEY;
            // curatedPostalCode: 4520-461
            if (curatedPostalCode.length() > 0) {
                endpoint = endpoint + "&postalCode=" + URLEncoder.encode(curatedPostalCode, "UTF-8");
            }
            // If there is locality
            if (curatedLocality.length() > 0) {
                endpoint = endpoint + "&locality=" + URLEncoder.encode(curatedLocality, "UTF-8");
            }
            return endpoint;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String curatePostalCode(String addressCode) {
        return addressCode.replaceAll("[^0123456789-]", "");
    }

    private String curateLocality(String addressCode) {
        return addressCode.replaceAll("\\d|-", "").trim();
    }
}
