package controllers;

import models.avaliacao.Caso;
import repository.CasoRepository;

import java.util.LinkedList;
import java.util.List;

public class ListCasosController {

    private final CasoRepository repository = new CasoRepository();

    public List<Caso> listCasos() {
        List<Caso> result = new LinkedList<>();

        for (Caso c : repository.findAll()) {
            repository.detach(c);
            result.add(c);
        }

        return result;
    }

}
