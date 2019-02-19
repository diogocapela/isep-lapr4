package controllers;

import adapters.MatrizIO;
import models.matriz.Matriz;
import repository.MatrizRepository;

public class ImportMatrizBaseController {

    private final MatrizRepository repository = new MatrizRepository();
    private final MatrizIO matrizIO = new MatrizIO();

    public Long importMatrizBase(String filePath) {
        Matriz m = matrizIO.importBase(filePath);
        if (m != null) {
            Matriz m2 = repository.add(m);
            return m2.getMatrizID();
        }
        return 0L;
    }

}
