package georef.sg05;


import georef.GeoRef;
import georef.models.Route;
import georef.strategies.sg05.SG05;
import georef.strategies.sg05.SG05Bing;
import georef.strategies.sg05.SG05Google;
import org.junit.Assert;
import org.junit.Test;

public class SG05Test {

    private GeoRef geoRef;

    @Test
    public void garantirQueDevolveMediaDeValoresDosDoisServicosAtravesDeEndereços() {
        Route googleRoute = new SG05Google().getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", "walking");
        Route bingRoute = new SG05Bing().getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", "walking");
        Route route = new SG05().getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", "walking");
        Double distanceExpected = (googleRoute.getDistance() + bingRoute.getDistance()) / 2;
        Double timeExpected = (googleRoute.getTime() + bingRoute.getTime()) / 2;

        Assert.assertTrue("A distancia devia ser igual.", route.getDistance().equals(distanceExpected));
        Assert.assertTrue("O tempo devia ser igual.", route.getTime().equals(timeExpected));
    }

    @Test
    public void garantirQueDevolveMediaDeValoresDosDoisServicosAtravesDeCoordenadas() {
        Route googleRoute = new SG05Google().getRouteBetweenLocations(41.150098, -8.614949, 41.155571, -8.613799, "driving");
        Route bingRoute = new SG05Bing().getRouteBetweenLocations(41.150098, -8.614949, 41.155571, -8.613799, "driving");
        Route route = new SG05().getRouteBetweenLocations(41.150098, -8.614949, 41.155571, -8.613799, "driving");
        Double distanceExpected = (googleRoute.getDistance() + bingRoute.getDistance()) / 2;
        Double timeExpected = (googleRoute.getTime() + bingRoute.getTime()) / 2;

        Assert.assertTrue("A distancia devia ser igual.", route.getDistance().equals(distanceExpected));
        Assert.assertTrue("O tempo devia ser igual.", route.getTime().equals(timeExpected));

    }


}
