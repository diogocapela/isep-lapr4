package services;

import georef.GeoRef;
import georef.GeoRefFactory;
import georef.models.Location;
import georef.models.Route;
import models.Envolvente;
import models.FatorRisco;
import models.Rating;
import modelsDTO.FatorRiscoDTO;

public class GeoReferenceService implements GeoReferenceServiceInterface {

    private GeoRef geoRef = GeoRefFactory.createGeoRef();

    public Rating getGeoRating(Envolvente envolvente, FatorRisco fatorRisco, Double objLat, Double objLng) {
        Rating result = Rating.MEDIUM;
        Double envLat = envolvente.toDTO().getLatitude();
        Double envLng = envolvente.toDTO().getLongitude();

        if (envLat == null || envLng == null) {
            return result;
        }

        Double testVal;
        FatorRiscoDTO fatorRiscoDTO = fatorRisco.toDTO();

        if (fatorRiscoDTO.getTitulo().toLowerCase().contains("distancia")) {
            Route route = geoRef.getRouteBetweenLocations(envLat, envLng, objLat, objLng, null);
            if (route != null) {
                testVal = route.getDistance();
                result = fatorRisco.determinarEscala(testVal);
            }
        } else if (fatorRiscoDTO.getTitulo().toLowerCase().contains("tempo")) {

            Route route = geoRef.getRouteBetweenLocations(envLat, envLng, objLat, objLng, null);
            if (route != null) {
                testVal = route.getTime();
                result = fatorRisco.determinarEscala(testVal);
            }
            // quantidade é indiferente
        } else if (fatorRiscoDTO.getTitulo().toLowerCase().contains("quantidade")) {
            testVal = (double) geoRef.getLocationsByType(envolvente.toDTO().getTitulo(), 5000, new Location(objLat, objLng)).size();
            result = fatorRisco.determinarEscala(testVal);
        }

        return result;
    }
}
