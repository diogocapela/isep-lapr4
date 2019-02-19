package georef.ui;

import georef.GeoRef;
import georef.GeoRefFactory;
import georef.Settings;
import georef.models.Location;
import georef.models.Route;
import georef.strategies.sg05.SG05;
import georef.strategies.sg06.SG06;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        for (String s : args) {
            if ("-mysql".equals(s))
                Settings.DB_PU = "DATABASE_02";
            if ("-local".equals(s))
                Settings.DB_PU = "DATABASE_01";
        }

        GeoRef geoDefault = GeoRefFactory.createGeoRef();
        GeoRef geoGoogle = GeoRefFactory.createGeoRefGoogle();
        GeoRef geoBing = GeoRefFactory.createGeoRefBing();

        // SG01
        Location sg01_default = geoDefault.getLocationFromPostalCode("4350 Porto");
        Location sg01_google = geoGoogle.getLocationFromPostalCode("4350 Porto");
        Location sg01_bing = geoBing.getLocationFromPostalCode("4350 Porto");
        System.out.println("SG01 Default (" + Settings.DEFAULT_STRATEGY + "):");
        System.out.println(sg01_default);
        System.out.println("SG01 Google:");
        System.out.println(sg01_google);
        System.out.println("SG01 Bing:");
        System.out.println(sg01_bing);
        System.out.println("\n\n");

        // SG02
        Location sg02_default = geoDefault.enrichPostalLocation("4350 Porto");
        Location sg02_google = geoGoogle.enrichPostalLocation("4350 Porto");
        Location sg02_bing = geoBing.enrichPostalLocation("4350 Porto");
        System.out.println("SG02 Default (" + Settings.DEFAULT_STRATEGY + "):");
        System.out.println(sg02_default);
        System.out.println("SG02 Google:");
        System.out.println(sg02_google);
        System.out.println("SG02 Bing:");
        System.out.println(sg02_bing);
        System.out.println("\n\n");


        {
            //SG03 google
            Location sg03_default = geoDefault.getLocationFromPostalCode("4510-121 Porto");
            System.out.println("SG03 default (Bombeiros) 10km max");
            LinkedList<Location> retLocs = geoDefault.getLocationsByType("Bombeiros", 0 /*default is 10km*/, sg03_default);
            for (Location l : retLocs) {
                System.out.println(l);
            }
        }
        {
            //SG03 google
            Location sg03_default = geoGoogle.getLocationFromPostalCode("4510-121 Porto");
            System.out.println("SG03Google (Bombeiros) 10km max");
            LinkedList<Location> retLocs = geoDefault.getLocationsByType("Bombeiros", 0 /*default is 10km*/, sg03_default);
            for (Location l : retLocs) {
                System.out.println(l);
            }
        }
        {
            //SG03 bing
            Location sg03_default = geoBing.getLocationFromPostalCode("WA 98104, EUA");
            System.out.println("SG03Bing (Fireman) 10km max");
            LinkedList<Location> retLocs = geoBing.getLocationsByType("Fireman", 0 /*default is 10km*/, sg03_default);
            for (Location l : retLocs) {
                System.out.println(l);
            }
        }

        // SG04
        Location sg04_default = geoDefault.seaLevelLocation("4350 Porto");
        Location sg04_google = geoGoogle.seaLevelLocation("4350 Porto");
        Location sg04_bing = geoBing.seaLevelLocation("4350 Porto");
        Double sg04_average = geoBing.getSeaLevelAltitudeMedia("4350 Porto");
        System.out.println("SG04 Default (" + Settings.DEFAULT_STRATEGY + "):");
        System.out.println(sg04_default);
        System.out.println("SG04 Google:");
        System.out.println(sg04_google);
        System.out.println("SG04 Bing:");
        System.out.println(sg04_bing);
        System.out.println("SG04 Average Altitude Between Google & Bing:");
        System.out.println(sg04_average);
        System.out.println("\n\n");

        //SG05

        Route sg05_default = geoDefault.getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", "driving");
        Route sg05_default2 = geoDefault.getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", "walking");
        Route sg05_default3 = geoDefault.getRouteBetweenLocations(41.150098, -8.614949, 41.155571, -8.613799, "driving");
        Route sg05_default4 = geoDefault.getRouteBetweenLocations(41.150098, -8.614949, 41.155571, -8.613799, null);
        System.out.println("SG05 Default (" + Settings.DEFAULT_STRATEGY + "):");
        System.out.println(sg05_default);
        System.out.println("SG05 Default2 (" + Settings.DEFAULT_STRATEGY + "):");
        System.out.println(sg05_default2);
        System.out.println("SG05 Default3 (" + Settings.DEFAULT_STRATEGY + "):");
        System.out.println(sg05_default3);

        Route sg05_google = geoGoogle.getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", "driving");
        Route sg05_google2 = geoGoogle.getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", "walking");
        Route sg05_google3 = geoGoogle.getRouteBetweenLocations(41.150098, -8.614949, 41.155571, -8.613799, "driving");
        System.out.println("SG05 Google:");
        System.out.println(sg05_google);
        System.out.println("SG05 Google2:");
        System.out.println(sg05_google2);
        System.out.println("SG05 Google3:");
        System.out.println(sg05_google3);

        Route sg05_bing = geoBing.getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", "driving");
        Route sg05_bing2 = geoBing.getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", "walking");
        Route sg05_bing3 = geoBing.getRouteBetweenLocations(41.150098, -8.614949, 41.155571, -8.613799, "driving");
        System.out.println("SG05 Bing:");
        System.out.println(sg05_bing);
        System.out.println("SG05 Bing2:");
        System.out.println(sg05_bing2);
        System.out.println("SG05 Bing3:");
        System.out.println(sg05_bing3);

        Route sg05_route_final = new SG05().getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", "driving");
        Route sg05_route_final2 = new SG05().getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", "walking");
        Route sg05_route_final3 = new SG05().getRouteBetweenLocations(41.150098, -8.614949, 41.155571, -8.613799, "driving");
        System.out.println("SG05 Route Final:");
        System.out.println(sg05_route_final);
        System.out.println("SG05 Route Final2:");
        System.out.println(sg05_route_final2);
        System.out.println("SG05 Route Final3:");
        System.out.println(sg05_route_final3);
        System.out.println("\n\n");

        //Miradouro da Senhora do Monte
        String addressA = "1170-253, Lisboa";
        //Cais do Sodré
        String addressB = "1200-161, Lisboa";
        Double sg06_google = geoGoogle.getElevationDifferenceBetweenLocations(addressA, addressB);
        Double sg06_bing = geoBing.getElevationDifferenceBetweenLocations(addressA, addressB);
        Double sg06_average = new SG06().getElevationDifferenceBetweenLocations(addressA, addressB);
        Double sg06_default = geoDefault.getElevationDifferenceBetweenLocations(addressA, addressB);


        System.out.println("SG06 Google:");
        System.out.println(sg06_google);
        System.out.println("\nSG06 Bing:");
        System.out.println(sg06_bing);
        System.out.println("\nSG06 Average Altitude of Google & Bing :");
        System.out.println(sg06_average);
        System.out.println("\nSG06 Default (" + Settings.DEFAULT_STRATEGY + "):");
        System.out.println(sg06_default);
    }

}
