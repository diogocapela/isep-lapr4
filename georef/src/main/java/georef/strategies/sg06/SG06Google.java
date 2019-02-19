package georef.strategies.sg06;

import georef.models.Location;
import georef.strategies.sg04.SG04Google;

public class SG06Google {


    public Double getElevationDifferenceBetweenLocations(String addressA, String addressB) {

        Location locationGoogleA = new SG04Google().seaLevelLocation(addressA);
        Location locationGoogleB = new SG04Google().seaLevelLocation(addressB);

        Double averageElevationBetweenLocations = locationGoogleA.getAltitude() - locationGoogleB.getAltitude();
        return averageElevationBetweenLocations;
    }

}
