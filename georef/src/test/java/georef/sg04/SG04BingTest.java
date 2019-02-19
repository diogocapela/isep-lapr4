package georef.sg04;

import georef.GeoRef;
import georef.models.Location;
import georef.strategies.BingGeoRefStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SG04BingTest {

    private GeoRef geoRef;

    public SG04BingTest() {
    }

    @Before
    public void setUp() {
        geoRef = new GeoRef(new BingGeoRefStrategy());
    }

    @Test
    public void seaLevelLocation() {
        Location location = geoRef.seaLevelLocation("4350 Porto");
        Double expectedLat = 40.9574089050293;
        Double expectedLng = -8.57882022857666;
        Double resultLat = location.getLatitude();
        Double resultLng = location.getLongitude();
        assertEquals(expectedLat, resultLat);
        assertEquals(expectedLng, resultLng);
    }
}
