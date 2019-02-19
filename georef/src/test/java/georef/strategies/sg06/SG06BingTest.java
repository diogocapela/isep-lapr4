package georef.strategies.sg06;

import georef.GeoRef;
import georef.strategies.BingGeoRefStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SG06BingTest {

    private GeoRef geoRef;


    @BeforeEach
    public void setUp() {
        geoRef = new GeoRef(new BingGeoRefStrategy());
    }


    @Test
    void getElevationDifferenceBetweenLocations() {
        String addressA = "1170-112, Lisboa";
        String addressB = "1100-278, Lisboa";
        Double elevationDifferenceBetweenLocationsResultBing = geoRef.getElevationDifferenceBetweenLocations(addressA, addressB);
        Double expectedElevationAddressA = 102.0;
        Double expectedElevationAddressB = 3.0;
        //Uma vez que a Bing considera que Lisboa está toda à mesma altitude quando corrigirem a informação este teste irá falhar
        Double expectedResult = 0.0;
        assertTrue(expectedResult >= elevationDifferenceBetweenLocationsResultBing * .95 && expectedResult <= elevationDifferenceBetweenLocationsResultBing * 95);
    }
}
