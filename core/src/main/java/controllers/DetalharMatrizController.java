package controllers;

import adapters.MatrizIO;
import models.matriz.Matriz;
import repository.MatrizRepository;

public class DetalharMatrizController {

    private final MatrizRepository repository = new MatrizRepository();
    private final MatrizIO matrizIO = new MatrizIO();

    public boolean detalharMatriz(Long ID, String filePath) {

        Matriz carac = repository.findById(ID);

        Matriz mc = matrizIO.importDetalhe(carac, filePath);

        if (mc != null) {
            return repository.update(mc) != null;
        } else {
            return false;
        }
    }

}
