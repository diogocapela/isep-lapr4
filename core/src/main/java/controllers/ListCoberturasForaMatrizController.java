package controllers;

import models.Cobertura;
import models.matriz.Matriz;
import modelsDTO.CelulaBaseDTO;
import repository.CoberturaRepository;
import repository.MatrizRepository;

import java.util.List;

public class ListCoberturasForaMatrizController {
    private final MatrizRepository matrizRepository = new MatrizRepository();
    private final CoberturaRepository coberturaRepository = new CoberturaRepository();

    public List<Cobertura> listCoberturasForaMatriz(Long ID) {
        Matriz m = matrizRepository.findById(ID);

        if (m != null) {
            matrizRepository.detach(m);
        } else
            return null;

        List<Cobertura> result = coberturaRepository.findAll();
        for (Cobertura cob : result) {
            coberturaRepository.detach(cob);
        }

        for (CelulaBaseDTO cDTO : m.toDTO().getCelulas()) {
            result.remove(new Cobertura(cDTO.getCoberturaDTO().getTitulo(), cDTO.getCoberturaDTO().getDescricao()));
        }

        return result;

    }

}
