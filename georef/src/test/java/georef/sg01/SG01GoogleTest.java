package georef.sg01;

import georef.GeoRef;
import georef.models.Location;
import georef.strategies.GoogleGeoRefStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SG01GoogleTest implements SG01Test {

    private GeoRef geoRef;

    public SG01GoogleTest() {
    }

    @Before
    public void setUp() {
        geoRef = new GeoRef(new GoogleGeoRefStrategy());
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowAnExceptionWhenPostalCodeIsNull() {
        geoRef.getLocationFromPostalCode(null);
    }

    @Test
    public void itShouldWorkForKnownPostalCodeNumberOne() {
        Location location = geoRef.getLocationFromPostalCode("4350 Porto");
        Double expectedLat = 40.9643248;
        Double expectedLng = -8.5797727;
        Double resultLat = location.getLatitude();
        Double resultLng = location.getLongitude();
        assertEquals(expectedLat, resultLat);
        assertEquals(expectedLng, resultLng);
    }

    @Test
    public void itShouldWorkForKnownPostalCodeNumberTwo() {
        Location location = geoRef.getLocationFromPostalCode("4100-421 Cedofeita");
        Double expectedLat = 41.1592527;
        Double expectedLng = -8.6330784;
        Double resultLat = location.getLatitude();
        Double resultLng = location.getLongitude();
        assertEquals(expectedLat, resultLat);
        assertEquals(expectedLng, resultLng);
    }

    @Test
    public void itShouldReturnNullForUnknownPostalCode() {
        Location result = geoRef.getLocationFromPostalCode("Unformatted and Unexistent Postal Code");
        assertEquals(null, result);
    }

}
