package controllers;

import models.FatorRisco;
import models.matriz.Matriz;
import modelsDTO.CelulaBaseDTO;
import repository.FatorRiscoRepository;
import repository.MatrizRepository;

import java.util.List;

public class ListFatoresForaMatrizController {
    private final MatrizRepository matrizRepository = new MatrizRepository();
    private final FatorRiscoRepository fatorRepository = new FatorRiscoRepository();

    public List<FatorRisco> listFatoresForaMatriz(Long ID) {
        Matriz m = matrizRepository.findById(ID);

        if (m != null) {
            matrizRepository.detach(m);
        } else
            return null;

        List<FatorRisco> result = fatorRepository.findAll();
        for (FatorRisco fr : result) {
            fatorRepository.detach(fr);
        }

        for (CelulaBaseDTO cDTO : m.toDTO().getCelulas()) {
            result.remove(new FatorRisco(cDTO.getFatorRiscoDTO().getTitulo(), cDTO.getFatorRiscoDTO().getDescricao()));
        }

        return result;

    }

}
