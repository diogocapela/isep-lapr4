package georef.strategies.sg05;

import georef.models.Route;

public class SG05 {

    ////////////////////////////////// WITH ADDRESS

    public Route getRouteBetweenLocations(String addressA, String addressB, String locomocao) {

        Route routeBing = new SG05Bing().getRouteBetweenLocations(addressA, addressB, locomocao);
        Route routeGoogle = new SG05Google().getRouteBetweenLocations(addressA, addressB, locomocao);

        Double distanceBing = routeBing.getDistance();
        Double distanceGoogle = routeGoogle.getDistance();
        Double timeBing = routeBing.getTime();
        Double timeGoogle = routeGoogle.getTime();

        Double mediumDistance = (distanceBing + distanceGoogle) / 2;
        Double mediumTime = (timeBing + timeGoogle) / 2;

        Route res = new Route(routeGoogle.getLocationA(), routeGoogle.getLocationB(), mediumDistance, mediumTime, routeGoogle.getLocomocao());

        return res;
    }

    /////////////////////////////////// WITH COORDINATES

    public Route getRouteBetweenLocations(Double latA, Double longA, Double latB, Double longB, String locomocao) {

        Route routeBing = new SG05Bing().getRouteBetweenLocations(latA, longA, latB, longB, locomocao);
        Route routeGoogle = new SG05Google().getRouteBetweenLocations(latA, longA, latB, longB, locomocao);

        if (routeBing == null || routeGoogle == null) {
            return null;
        }

        Double distanceBing = routeBing.getDistance();
        Double distanceGoogle = routeGoogle.getDistance();
        Double timeBing = routeBing.getTime();
        Double timeGoogle = routeGoogle.getTime();

        Double mediumDistance = (distanceBing + distanceGoogle) / 2;
        Double mediumTime = (timeBing + timeGoogle) / 2;

        Route res = new Route(routeGoogle.getLocationA(), routeGoogle.getLocationB(), mediumDistance, mediumTime, routeGoogle.getLocomocao());

        return res;
    }

}
