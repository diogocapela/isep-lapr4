package controllers;

import models.avaliacao.Caso;
import services.ProcessCasoService;
import utils.JSONExporter;
import utils.JSONImporter;


public class ProcessCasoFromJSONController {
    private final JSONImporter jsonImporter = new JSONImporter();
    private final JSONExporter jsonExporter = new JSONExporter();

    public Long processCasoFromJSON(String filePathInput, String filePathOutput) {
        // IMPORTAR
        Caso caso = null;
        try {
            caso = jsonImporter.importCasoFromFile(filePathInput);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        ProcessCasoService processCasoService = new ProcessCasoService();
        Caso casoReal = processCasoService.processarCaso(caso);

        // EXPORTAR
        try {
            jsonExporter.exportCaso(casoReal, filePathOutput);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return casoReal.getID();
    }
}
