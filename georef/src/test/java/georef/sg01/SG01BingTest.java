package georef.sg01;

import georef.GeoRef;
import georef.models.Location;
import georef.strategies.BingGeoRefStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SG01BingTest implements SG01Test {

    private GeoRef geoRef;

    public SG01BingTest() {
    }

    @Before
    public void setUp() {
        geoRef = new GeoRef(new BingGeoRefStrategy());
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowAnExceptionWhenPostalCodeIsNull() {
        geoRef.getLocationFromPostalCode(null);
    }


    @Test
    public void itShouldWorkForKnownPostalCodeNumberOne() {
        Location location = geoRef.getLocationFromPostalCode("4350 Porto");
        Double expectedLat = 40.9574089050293;
        Double expectedLng = -8.57882022857666;
        Double resultLat = location.getLatitude();
        Double resultLng = location.getLongitude();
        assertEquals(expectedLat, resultLat);
        assertEquals(expectedLng, resultLng);
    }

    @Test
    public void itShouldWorkForKnownPostalCodeNumberTwo() {
        Location location = geoRef.getLocationFromPostalCode("4100-421 Cedofeita");
        Double expectedLat = 41.15599822998047;
        Double expectedLng = -8.623000144958496;
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
