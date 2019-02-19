package georef;

import georef.models.Location;
import georef.models.Route;

import java.util.LinkedList;

public interface GeoRefStrategy {

    // SG01
    Location getLocationFromPostalCode(String address);

    // SG02
    Location enrichPostalLocation(String address);

    //SG03
    LinkedList<Location> getLocationsByType(String envolvente, Integer radiusMeters, Location referenceLocation);

    // SG04
    Location seaLevelLocation(String address);

    Double getSeaLevelAltitudeMedia(String address);

    // SG05
    Route getRouteBetweenLocations(String addressA, String addressB, String locomocao);

    Route getRouteBetweenLocations(Double latA, Double longA, Double latB, Double longB, String locomocao);

    //SG06
    Double getElevationDifferenceBetweenLocations(String addressA, String addressB);

    // SGImage
    String downloadImageOfAddress(String address);

}
