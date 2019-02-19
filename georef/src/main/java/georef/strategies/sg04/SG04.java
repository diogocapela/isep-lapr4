package georef.strategies.sg04;

import georef.models.Location;

public class SG04 {

    public Double getSeaLevelAltitudeMedia(String postalCode) {
        Location googleLocation = new SG04Google().seaLevelLocation(postalCode);
        Location bingLocation = new SG04Bing().seaLevelLocation(postalCode);
        Double altitudeGoogle = googleLocation.getAltitude();
        Double altitudeBing = bingLocation.getAltitude();

        return (altitudeGoogle + altitudeBing) / 2;
    }

}
