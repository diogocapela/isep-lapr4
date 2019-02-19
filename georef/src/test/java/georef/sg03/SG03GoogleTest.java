package georef.sg03;

import georef.GeoRef;
import georef.GeoRefFactory;
import georef.models.Location;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

public class SG03GoogleTest {

    GeoRef geoGoogle;

    public SG03GoogleTest() {
        geoGoogle = GeoRefFactory.createGeoRefGoogle();
    }

    @Test
    public void testGoogle() {
        Location sg03_default = geoGoogle.getLocationFromPostalCode("4510-121 Porto");
        System.out.println("SG03Google (Bombeiros) 10km max");
        LinkedList<Location> retLocs = geoGoogle.getLocationsByType("Bombeiros", 0 /*default is 10km*/, sg03_default);
        Assert.assertTrue(retLocs.size() == 20);
    }
}
