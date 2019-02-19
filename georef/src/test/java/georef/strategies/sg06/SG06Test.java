package georef.strategies.sg06;

import georef.GeoRef;
import georef.strategies.BingGeoRefStrategy;
import georef.strategies.GoogleGeoRefStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SG06Test {

    private GeoRef geoRefGoogle;
    private GeoRef geoRefBing;

    @BeforeEach
    public void setUp() {
        geoRefGoogle = new GeoRef(new GoogleGeoRefStrategy());
        geoRefBing = new GeoRef(new BingGeoRefStrategy());
    }

    @Test
    void getElevationDifferenceBetweenLocations() {


    }
}
