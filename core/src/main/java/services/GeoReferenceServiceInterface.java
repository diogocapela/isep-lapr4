package services;

import models.Envolvente;
import models.FatorRisco;
import models.Rating;

public interface GeoReferenceServiceInterface {

    Rating getGeoRating(Envolvente envolvente, FatorRisco fatorRisco, Double latitude, Double longitude);

}
