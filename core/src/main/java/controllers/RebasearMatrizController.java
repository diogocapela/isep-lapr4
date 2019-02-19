package controllers;

import models.matriz.Matriz;
import repository.MatrizRepository;

public class RebasearMatrizController {

    private final MatrizRepository repository = new MatrizRepository();

    public Long rebasearMatriz(long ID) {
        Matriz oldMatriz = repository.findById(ID);

        Matriz newMatriz = oldMatriz.rebasearMatriz();

        if (newMatriz != null) {
            repository.add(newMatriz);
            return newMatriz.getMatrizID();
        }

        return null;
    }
}
