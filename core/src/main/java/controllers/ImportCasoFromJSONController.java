package controllers;

import models.Cobertura;
import models.avaliacao.Caso;
import models.avaliacao.ObjetoSeguro;
import modelsDTO.CoberturaDTO;
import modelsDTO.ObjetoSeguroDTO;
import repository.CasoRepository;
import repository.ObjetoSeguroRepository;
import utils.JSONImporter;

import java.util.LinkedList;
import java.util.List;

public class ImportCasoFromJSONController {

    private final CasoRepository casoRepository = new CasoRepository();
    private final ObjetoSeguroRepository objetoSeguroRepository = new ObjetoSeguroRepository();
    private final JSONImporter jsonImporter = new JSONImporter();

    public Long importCasoFromJSON(String filePath) throws Exception {
        Caso caso = jsonImporter.importCasoFromFile(filePath);
        Caso casoTmp = new Caso();
        if (caso != null) {
            for (ObjetoSeguroDTO os : caso.toDTO().getObjetosSegurados()) {
                ObjetoSeguro osTmp = new ObjetoSeguro();
                objetoSeguroRepository.add(osTmp);
                List<Cobertura> coberturas = new LinkedList<>();
                for (CoberturaDTO cDTO : os.getCoberturas()) {
                    coberturas.add(new Cobertura(cDTO.getTitulo(), cDTO.getDescricao()));
                }
                ObjetoSeguro osFalso = new ObjetoSeguro(os.getDescricao(), os.getLatitude(), os.getLatitude(), coberturas);
                osTmp.copyAttributes(osFalso);
                objetoSeguroRepository.update(osTmp);
            }

            casoRepository.add(casoTmp);
            casoTmp.copyAttributes(caso);
            casoRepository.update(casoTmp);
        } else {
            return null;
        }

        return casoTmp.getID();
    }

}
