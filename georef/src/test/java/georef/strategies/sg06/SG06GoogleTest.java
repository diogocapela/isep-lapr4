package georef.strategies.sg06;

import georef.GeoRef;
import georef.strategies.GoogleGeoRefStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SG06GoogleTest {

    private GeoRef geoRef;

    @BeforeEach
    public void setUp() {
        geoRef = new GeoRef(new GoogleGeoRefStrategy());
    }


    @Test
    void getElevationDifferenceBetweenLocations() {
        String addressA = "1170-253, Lisboa";
        String addressB = "1200-161, Lisboa";
        Double elevationDifferenceBetweenLocationsResultGoogle = geoRef.getElevationDifferenceBetweenLocations(addressA, addressB);
        Double expectedElevationAddressA = 102.0;
        Double expectedElevationAddressB = 3.0;
        Double expectedResult = expectedElevationAddressA - expectedElevationAddressB;

        assertTrue(expectedResult > elevationDifferenceBetweenLocationsResultGoogle * .95 && expectedResult < elevationDifferenceBetweenLocationsResultGoogle * 95);
    }

}

