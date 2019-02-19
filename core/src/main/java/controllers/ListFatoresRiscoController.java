package controllers;

import models.FatorRisco;
import repository.FatorRiscoRepository;

import java.util.LinkedList;
import java.util.List;

public class ListFatoresRiscoController {
    private final FatorRiscoRepository repository = new FatorRiscoRepository();

    public List<FatorRisco> listFatoresRisco() {
        List<FatorRisco> result = new LinkedList<>();

        for (FatorRisco fr : repository.findAll()) {
            repository.detach(fr);
            result.add(fr);
        }

        return result;
    }

}
