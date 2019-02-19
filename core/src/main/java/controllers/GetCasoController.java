package controllers;

import models.avaliacao.Caso;
import modelsDTO.CasoDTO;
import repository.CasoRepository;

public class GetCasoController {

    private final CasoRepository repository = new CasoRepository();

    public CasoDTO getCasoByID(String idCasoEnpoint) {
        try {
            Long parseLong = Long.parseLong(idCasoEnpoint);
            Caso caso = repository.findById(parseLong);
            repository.detach(caso);
            return caso.toDTO();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
