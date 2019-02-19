package georef.strategies;

import georef.GeoRefStrategy;
import georef.models.Location;
import georef.models.Route;
import georef.strategies.sg01.SG01Bing;
import georef.strategies.sg02.SG02Bing;
import georef.strategies.sg03.SG03Bing;
import georef.strategies.sg04.SG04;
import georef.strategies.sg04.SG04Bing;
import georef.strategies.sg05.SG05Bing;
import georef.strategies.sg06.SG06Bing;
import georef.strategies.sgimage.SGImageBing;

import java.util.LinkedList;

public class BingGeoRefStrategy implements GeoRefStrategy {

    // SG01
    @Override
    public Location getLocationFromPostalCode(String postalCode) {
        return new SG01Bing().getLocationFromPostalCode(postalCode);
    }

    // SG02
    @Override
    public Location enrichPostalLocation(String postalCode) {
        return new SG02Bing().enrichPostalLocation(postalCode);
    }

    // SG03
    @Override
    public LinkedList<Location> getLocationsByType(String envolvente, Integer radiusMeters, Location referenceLocation) {
        return new SG03Bing().getLocationsByType(envolvente, radiusMeters, referenceLocation);
    }

    // SG04
    @Override
    public Location seaLevelLocation(String postalCode) {
        return new SG04Bing().seaLevelLocation(postalCode);
    }

    @Override
    public Double getSeaLevelAltitudeMedia(String address) {
        return new SG04().getSeaLevelAltitudeMedia(address);
    }

    // SG05
    @Override
    public Route getRouteBetweenLocations(String addressA, String addressB, String locomocao) {
        return new SG05Bing().getRouteBetweenLocations(addressA, addressB, locomocao);
    }

    @Override
    public Route getRouteBetweenLocations(Double latA, Double longA, Double latB, Double longB, String locomocao) {
        return new SG05Bing().getRouteBetweenLocations(latA, longA, latB, longB, locomocao);
    }

    //SG06
    @Override
    public Double getElevationDifferenceBetweenLocations(String addressA, String addressB) {
        return new SG06Bing().getElevationDifferenceBetweenLocations(addressA, addressB);
    }

    // SGImage
    @Override
    public String downloadImageOfAddress(String address) {
        return new SGImageBing().downloadImageOfAddress(address);
    }

    /*@Override
    public Route getRouteBetweenCoordinates(Double latA, Double longA, Double latB, Double longB, String locomocao) {
        return new SG05Bing().getRouteBetweenCoordinates(latA, longA, latB, longB, locomocao);
    }*/

}
