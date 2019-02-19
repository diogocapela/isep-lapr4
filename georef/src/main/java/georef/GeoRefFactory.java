package georef;

import georef.strategies.BingGeoRefStrategy;
import georef.strategies.GoogleGeoRefStrategy;

public class GeoRefFactory {

    public static GeoRef createGeoRef() {
        if ("Google".equalsIgnoreCase(Settings.DEFAULT_STRATEGY)) {
            return new GeoRef(new GoogleGeoRefStrategy());
        } else {
            return new GeoRef(new BingGeoRefStrategy());
        }
    }

    public static GeoRef createGeoRefGoogle() {
        return new GeoRef(new GoogleGeoRefStrategy());
    }

    public static GeoRef createGeoRefBing() {
        return new GeoRef(new BingGeoRefStrategy());
    }

}
