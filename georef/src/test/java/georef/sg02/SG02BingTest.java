package georef.sg02;

import georef.GeoRef;
import georef.GeoRefFactory;
import georef.models.Location;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SG02BingTest implements SG02Test {

    private GeoRef geoRef;

    public SG02BingTest() {
    }

    @Before
    public void setUp() {
        geoRef = GeoRefFactory.createGeoRefBing();
    }

    @Test
    public void ensureCorrectCity() {
        String expectedCity = "Rebordosa";
        Location location = geoRef.enrichPostalLocation("4585-447 Rebordosa");
        Assert.assertEquals(expectedCity, location.getCity());
        expectedCity = "Cristelo";
        location = geoRef.enrichPostalLocation("4580-194 Cristelo");
        Assert.assertEquals(expectedCity, location.getCity());
    }

    @Test
    public void ensureCorrectCountry() {
        String expectedCountry = "Portugal";
        Location location = geoRef.enrichPostalLocation("4585-447 Rebordosa");
        Assert.assertEquals(expectedCountry, location.getCountry());
        expectedCountry = "United States";
        location = geoRef.enrichPostalLocation("10001 New York");
        Assert.assertEquals(expectedCountry, location.getCountry());
    }

    @Test
    public void ensureCorrectDistrict() {
        String expectedDistrict = "Porto";
        Location location = geoRef.enrichPostalLocation("4585-447 Rebordosa");
        Assert.assertEquals(expectedDistrict, location.getDistrict());
        expectedDistrict = "NY";
        location = geoRef.enrichPostalLocation("10001 New York");
        Assert.assertEquals(expectedDistrict, location.getDistrict());
    }

    @Test
    public void ensureCorrectPostalCode() {
        String expectedPostalCode = "4585-447";
        Location location = geoRef.enrichPostalLocation("4585-447 Rebordosa");
        Assert.assertEquals(expectedPostalCode, location.getPostalCode());
        expectedPostalCode = "10001";
        location = geoRef.enrichPostalLocation("10001 New York");
        Assert.assertEquals(expectedPostalCode, location.getPostalCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowAnExceptionWhenPostalCodeIsNull() {
        geoRef.enrichPostalLocation(null);
    }

    @Test
    public void itShouldReturnNullForUnknownPostalCode() {
        Location location = geoRef.enrichPostalLocation("Unformatted and Unexistent Postal Code");
        assertEquals(null, location);
    }

    @Test
    public void itShouldReturnNullForUnknowLocation() {
        Location location = geoRef.enrichPostalLocation("4000-000 XXXXX");
        assertEquals(null, location);
    }
}
