package controllers;

import adapters.MatrizIO;
import models.matriz.Matriz;
import repository.MatrizRepository;

public class CaracterizarMatrizController {

    private final MatrizRepository repository = new MatrizRepository();
    private final MatrizIO matrizIO = new MatrizIO();

    public boolean caracterizarMatriz(Long ID, String filePath) {

        Matriz base = repository.findById(ID);

        Matriz mc = matrizIO.importCaracterizacao(base, filePath);

        if (mc != null) {
            return repository.update(mc) != null;
        } else {
            return false;
        }
    }

}
