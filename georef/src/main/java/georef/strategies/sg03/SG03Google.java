package georef.strategies.sg03;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import georef.Settings;
import georef.models.Location;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.LinkedList;

public class SG03Google {

    private Integer radius;


    public LinkedList<Location> getLocationsByType(String envolvente, Integer radiusMeters, Location referenceLocation) {
        if (radiusMeters == null || radiusMeters == 0) {
            this.radius = 10000;
        } else {
            this.radius = radiusMeters;
        }
        LinkedList<Location> locations = new LinkedList<>();

        try {

            GeoApiContext context = new GeoApiContext.Builder().apiKey(Settings.GOOGLE_API_KEY).build();
            NearbySearchRequest nsr = new NearbySearchRequest(context);

            //set the radius
            nsr.radius(this.radius);
            try {
                PlaceType place = PlaceType.valueOf(envolvente);
                nsr.type(place);
            } catch (IllegalArgumentException iae) {
                nsr.keyword(envolvente);
            }

            LatLng latng = new LatLng(referenceLocation.getLatitude(), referenceLocation.getLongitude());

            nsr.location(latng);

            //retreive
            PlacesSearchResponse results = nsr.await();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String responseJson = gson.toJson(results);

            // Parse the results from JSON to POJO

            JSONObject jo = (JSONObject) new JSONParser().parse(responseJson);


            JSONArray resultsArray = (JSONArray) jo.get("results");

            for (int i = 0; i < resultsArray.size(); i++) {
                JSONObject jsonObjectTemp = (JSONObject) resultsArray.get(i);
                JSONObject jsonLocationObject = (JSONObject) ((JSONObject) jsonObjectTemp.get("geometry")).get("location");
                String street = (String) jsonObjectTemp.get("vicinity");
                Location l = new Location(Double.parseDouble(jsonLocationObject.get("lat").toString()), Double.parseDouble(jsonLocationObject.get("lng").toString()));

                l.setTitle(jsonObjectTemp.get("name").toString());
                l.setStreet(street);
                locations.add(l);
            }
            return locations;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
