package controllers;


import adapters.MatrizIO;
import models.matriz.Matriz;
import repository.MatrizRepository;


public class ExportMatrizBaseController {
    private final MatrizRepository matrizRepository = new MatrizRepository();
    private final MatrizIO mio = new MatrizIO();

    public Long exportMatrizBase(String filePath, Long mID) {
        Matriz matriz = matrizRepository.findById(mID);
        if (matriz != null) {
            matrizRepository.detach(matriz);
            if (mio.exportMatriz(matriz, filePath)) {
                return 0L;
            }
        }
        return 1L;
    }

}
