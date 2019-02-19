package controllers;

import models.avaliacao.Caso;
import repository.CasoRepository;
import services.ProcessCasoService;
import utils.XMLImporter;

public class ProcessCasoFromXMLController {
    private final XMLImporter xmlImporter = new XMLImporter();
    private final CasoRepository casoRepository = new CasoRepository();

    public String processarCaso(String xmlString) {
        //IMPORTAR
        Caso caso = null;
        try {
            caso = xmlImporter.importCasoFromString(xmlString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        if (caso == null) {
            return null;
        }

        ProcessCasoService processCasoService = new ProcessCasoService();
        Caso casoReal = processCasoService.processarCaso(caso);

        // dar como processado ou colocar em validacao
        String outputMessageEnding;
        if (casoReal != null) {
            if (casoReal.hasValidacao()) {
                //casoReal.emValidacao();
                outputMessageEnding = "em validacão";
            } else {
                //casoReal.processado();
                outputMessageEnding = "processado";
            }
            //casoRepository.update(casoReal);
        } else {
            return "Caso fornecido encontra-se por avaliar!";
        }
        casoRepository.detach(casoReal);

        return String.format("Caso fornecido (ID=%d) foi avaliado e encontra-se %s.", casoReal.getID(), outputMessageEnding);
    }

}
