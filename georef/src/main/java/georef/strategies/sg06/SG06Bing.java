package georef.strategies.sg06;

import georef.models.Location;
import georef.strategies.sg04.SG04Bing;

public class SG06Bing {

    public Double getElevationDifferenceBetweenLocations(String addressA, String addressB) {

        Location locationBingA = new SG04Bing().seaLevelLocation(addressA);
        Location locationBingB = new SG04Bing().seaLevelLocation(addressB);

        Double elevationDifferenceBetweenLocations = locationBingA.getAltitude() - locationBingB.getAltitude();
        return elevationDifferenceBetweenLocations;
    }

}
