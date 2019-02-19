package georef.strategies;

import georef.GeoRefStrategy;
import georef.models.Location;
import georef.models.Route;

import java.util.LinkedList;

public class MockGeoRefStrategy implements GeoRefStrategy {

    //SG01
    @Override
    public Location getLocationFromPostalCode(String postalCode) {
        return new Location(123.0, 123.0);
    }

    //SG02
    @Override
    public Location enrichPostalLocation(String postalCode) {
        Location location = new Location();
        location.setCountry("Portugal");
        location.setDistrict("Porto");
        location.setCity("Rebordosa");
        location.setPostalCode("4585-447");
        return location;
    }

    // SG03
    @Override
    public LinkedList<Location> getLocationsByType(String envolvente, Integer radiusMeters, Location referenceLocation) {
        return null;
    }

    //SG04
    @Override
    public Location seaLevelLocation(String postalCode) {
        return null;
    }

    @Override
    public Double getSeaLevelAltitudeMedia(String address) {
        return null;
    }

    // SG05
    @Override
    public Route getRouteBetweenLocations(String addressA, String addressB, String locomocao) {
        Location locationA = new Location(41.149685, -8.6160801);//Travessa de Cedofeita, Porto
        Location locationB = new Location(41.1566068, -8.62117709999999);//Rua da Boavista, Porto
        Route route = new Route(locationA, locationB, 1.644, 364D, "driving");

        return route;
    }

    @Override
    public Route getRouteBetweenLocations(Double latA, Double longA, Double latB, Double longB, String locomocao) {
        Location locationA = new Location(41.149685, -8.6160801);//Travessa de Cedofeita, Porto
        Location locationB = new Location(41.1566068, -8.62117709999999);//Rua da Boavista, Porto
        Route route = new Route(locationA, locationB, 1.644, 364D, "driving");

        return route;
    }

    //SG06
    @Override
    public Double getElevationDifferenceBetweenLocations(String addressA, String addressB) {
        return null;
    }

    // SGImage
    @Override
    public String downloadImageOfAddress(String address) {
        return null;
    }


}
