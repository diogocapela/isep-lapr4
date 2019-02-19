package raconsole.controller;

import models.avaliacao.Caso;
import modelsDTO.CasoDTO;
import repository.CasoRepository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AR03Controller {

    private CasoRepository casoRepository = new CasoRepository();
    private List<Caso> PedidosdeAvaliacaodeRiscopendentesatribuidos;
    private List<CasoDTO> resulDTO;


    public List<String> getPedidosdeAvaliacaodeRiscopendentes(String nome) {
        List<String> resulUI = new LinkedList<>();

        this.PedidosdeAvaliacaodeRiscopendentesatribuidos = getCasosValidadoseAtribuidos(nome);
        if (PedidosdeAvaliacaodeRiscopendentesatribuidos.isEmpty()) {
            return resulUI;
        }

        //ordena os casos do mais antigo para o mais recente
        ordenarCasosMaisAntigoParaMaisRecente();

        //transforma casos em DTO
        resulDTO = getPedidosdeAvaliacaodeRiscoatribuidosDTO();
        if (resulDTO.isEmpty()) {
            return resulUI;
        }

        //devolve lista da diferença de tempos desde a atribuição ate a data actual
        for (CasoDTO tempCasoDto : resulDTO) {
            resulUI.add(tempCasoDto.getID() + " - Tempo decorrido desde a data de atribuição ate hoje é " + tempoOcorridoEntreDataAtribuidoeDataActual(tempCasoDto.getID()) + " segundos");
        }
        return resulUI;
    }

    //lista da diferença de tempos desde a atribuição ate a data actual
    private Long tempoOcorridoEntreDataAtribuidoeDataActual(Long idCaso) {
        Caso caso = casoRepository.findById(idCaso);
        return caso.tempoOcorridoEntreDataAtribuidoeDataActual();
    }

    //Ordenação
    private void ordenarCasosMaisAntigoParaMaisRecente() {
        Collections.sort(PedidosdeAvaliacaodeRiscopendentesatribuidos);
    }

    //Lista de casos de um Analista
    private List<Caso> getCasosValidadoseAtribuidos(String nome) {
        List<Caso> listaCasosValidacoesPendentes = casoRepository.fetchCasosValidadosAtribuidosPorAnalista(nome);
        return listaCasosValidacoesPendentes;
    }

    //toDTO
    private List<CasoDTO> getPedidosdeAvaliacaodeRiscoatribuidosDTO() {
        List<CasoDTO> resDTO = new LinkedList<>();
        //transforma caso em casoDTO
        for (Caso caso : PedidosdeAvaliacaodeRiscopendentesatribuidos) {
            resDTO.add(caso.toDTO());
        }
        return resDTO;
    }
}

