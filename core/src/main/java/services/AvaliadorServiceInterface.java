package services;

import models.avaliacao.Caso;
import modelsDTO.MatrizDTO;

public interface AvaliadorServiceInterface {

    //Avalia um caso a partir da matriz que está atualmente em vigor
    public Boolean avaliaCaso(
        Caso caso,
        MatrizDTO matriz,
        GeoReferenceService geoReferenceService
    );

}
