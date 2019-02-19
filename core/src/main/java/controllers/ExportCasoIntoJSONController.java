package controllers;

import models.avaliacao.Caso;
import repository.CasoRepository;
import utils.JSONExporter;

public class ExportCasoIntoJSONController {

    private final CasoRepository repository = new CasoRepository();
    private final JSONExporter jsonExporter = new JSONExporter();

    public void exportCasoIntoJSON(Long id, String filePath) throws Exception {
        Caso caso = repository.findById(id);
        jsonExporter.exportCaso(caso, filePath);
    }

}
