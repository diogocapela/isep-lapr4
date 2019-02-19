package controllers;

import models.Cobertura;
import repository.CoberturaRepository;

import java.util.LinkedList;
import java.util.List;

public class ListCoberturasControllers {

    private final CoberturaRepository repository = new CoberturaRepository();

    public List<Cobertura> listCoberturas() {
        List<Cobertura> result = new LinkedList<>();

        for (Cobertura c : repository.findAll()) {
            repository.detach(c);
            result.add(c);
        }

        return result;
    }

}
