package georef.sg04;

import georef.GeoRef;
import georef.models.Location;
import georef.strategies.GoogleGeoRefStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SG04GoogleTest {

    private GeoRef geoRef;

    public SG04GoogleTest() {
    }

    @Before
    public void setUp() {
        geoRef = new GeoRef(new GoogleGeoRefStrategy());
    }

    @Test
    public void seaLevelLocation() {
        Location location = geoRef.seaLevelLocation("4350 Porto");
        Double expectedLat = 40.9643248;
        Double expectedLng = -8.5797727;
        Double resultLat = location.getLatitude();
        Double resultLng = location.getLongitude();
        assertEquals(expectedLat, resultLat);
        assertEquals(expectedLng, resultLng);
    }
}
