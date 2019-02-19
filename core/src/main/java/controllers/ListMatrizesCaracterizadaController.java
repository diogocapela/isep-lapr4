package controllers;

import models.matriz.Matriz;
import repository.MatrizRepository;

import java.util.LinkedList;
import java.util.List;

public class ListMatrizesCaracterizadaController {
    private final MatrizRepository repository = new MatrizRepository();

    public List<Matriz> listMatrizesCaracterizadas() {
        List<Matriz> result = new LinkedList<>();

        for (Matriz m : repository.findAll()) {
            if (m.isCaracterizada()) {
                repository.detach(m);
                result.add(m);
            }
        }

        return result;
    }
}
