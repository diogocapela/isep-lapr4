package georef.ui;

import georef.GeoRef;
import georef.GeoRefFactory;

public class ImageMain {

    public static void main(String[] args) {
        GeoRef geoDefault = GeoRefFactory.createGeoRef();
        GeoRef geoGoogle = GeoRefFactory.createGeoRefGoogle();
        GeoRef geoBing = GeoRefFactory.createGeoRefBing();

        String address = "Rotunda da Boavista";

        // SGImage
        System.out.println("Default:");
        System.out.println(geoDefault.downloadImageOfAddress(address));
        System.out.println("Google:");
        System.out.println(geoGoogle.downloadImageOfAddress(address));
        System.out.println("Bing:");
        System.out.println(geoBing.downloadImageOfAddress(address));
    }

}
