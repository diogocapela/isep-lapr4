package georef;

import georef.models.Location;
import georef.models.Route;

import java.util.LinkedList;

public class GeoRef {

    private GeoRefStrategy strategy;

    public GeoRef(GeoRefStrategy strategy) {
        this.strategy = strategy;
    }

    public Location getLocationFromPostalCode(String postalCode) {
        return strategy.getLocationFromPostalCode(postalCode);
    }

    public Location enrichPostalLocation(String postalCode) {
        return strategy.enrichPostalLocation(postalCode);
    }

    public Location seaLevelLocation(String postalCode) {
        return strategy.seaLevelLocation(postalCode);
    }

    public Double getSeaLevelAltitudeMedia(String postalCode) {
        return strategy.getSeaLevelAltitudeMedia(postalCode);
    }


    public LinkedList<Location> getLocationsByType(String envolvente, Integer radiusMeters, Location referenceLocation) {
        return strategy.getLocationsByType(envolvente, radiusMeters, referenceLocation);
    }

    // SG05
    public Route getRouteBetweenLocations(String addressA, String addressB, String locomocao) {
        return strategy.getRouteBetweenLocations(addressA, addressB, locomocao);
    }

    public Route getRouteBetweenLocations(Double latA, Double longA, Double latB, Double longB, String locomocao) {
        return strategy.getRouteBetweenLocations(latA, longA, latB, longB, locomocao);
    }

    //SG06
    public Double getElevationDifferenceBetweenLocations(String addressA, String addressB) {
        return strategy.getElevationDifferenceBetweenLocations(addressA, addressB);
    }

    // SGImage
    public String downloadImageOfAddress(String address) {
        return strategy.downloadImageOfAddress(address);
    }

}

