package georef.sg03;

import georef.GeoRef;
import georef.GeoRefFactory;
import georef.models.Location;
import org.junit.Assert;

import java.util.LinkedList;

public class SG03BingTest {

    GeoRef geoBing;

    public SG03BingTest() {
        geoBing = GeoRefFactory.createGeoRefBing();
    }

    @org.junit.jupiter.api.Test
    void testBing() {
        Location sg03_default = geoBing.getLocationFromPostalCode("WA 98104, EUA");
        LinkedList<Location> retLocs = geoBing.getLocationsByType("Fireman", 0 /*default is 10km*/, sg03_default);
        Assert.assertTrue(retLocs.size() > 0);
    }
}
