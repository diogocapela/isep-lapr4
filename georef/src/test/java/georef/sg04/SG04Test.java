package georef.sg04;

import georef.GeoRef;
import georef.strategies.GoogleGeoRefStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SG04Test {


    private GeoRef geoRef;

    public SG04Test() {
    }

    @Before
    public void setUp() {
        geoRef = new GeoRef(new GoogleGeoRefStrategy());
    }

    @Test
    public void getSeaLevelAltitudeMedia() {
        Double location = geoRef.getSeaLevelAltitudeMedia("4350 Porto");
        Double expected = 109.0;
        assertEquals(expected, location);
    }

}
