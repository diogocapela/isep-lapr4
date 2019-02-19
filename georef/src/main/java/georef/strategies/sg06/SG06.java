package georef.strategies.sg06;

public class SG06 {

    public Double getElevationDifferenceBetweenLocations(String addressA, String addressB) {

        // diff bing
        Double elevationDifferenceBetweenLocationsBing = new SG06Bing().getElevationDifferenceBetweenLocations(addressA, addressB);

        //diff google
        Double elevationDifferenceBetweenLocationsGoogle = new SG06Google().getElevationDifferenceBetweenLocations(addressA, addressB);

        // avg
        Double elevationAverage = (elevationDifferenceBetweenLocationsGoogle + elevationDifferenceBetweenLocationsBing) / 2;

        return elevationAverage;


    }
}
