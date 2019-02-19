package controllers;

import models.Envolvente;
import modelsDTO.EnvolventeDTO;
import repository.EnvolventeRepository;

import java.util.LinkedList;
import java.util.List;

public class ListEnvolventesController {

    private final EnvolventeRepository repository = new EnvolventeRepository();

    public List<EnvolventeDTO> listEnvolventes() {
        List<EnvolventeDTO> result = new LinkedList<>();

        for (Envolvente e : repository.findAll()) {
            repository.detach(e);
            result.add(e.toDTO());
        }

        return result;
    }

}
