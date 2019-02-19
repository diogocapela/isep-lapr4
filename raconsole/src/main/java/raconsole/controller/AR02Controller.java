package raconsole.controller;

import georef.utils.LocationInfos;
import models.User;
import models.avaliacao.Caso;
import models.avaliacao.ObjetoSeguro;
import modelsDTO.CasoDTO;
import repository.CasoRepository;
import repository.UserRepository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AR02Controller {

    private CasoRepository casoRepository = new CasoRepository();
    private UserRepository userRepository = new UserRepository();
    private List<Caso> listaCasosValidacoesPendentes;

    /**
     * Método que devolve uma lista de Strings representativas dos casos
     * que contenham pelo menos um objecto seguro pertencentes ao distrito passado
     * por parametro e com pedidos de validação de avaliação de risco pendentes e
     * não atribuídos.
     *
     * @param distrito, String, distrito para filtragem
     * @return resUI, List<String>,
     */
    public List<String> getCasosEmValidacaoNaoAtribuidos(String distrito) {
        List<String> resUI = new LinkedList<>();

        //vai buscar casos com validacoes pendentes
        this.listaCasosValidacoesPendentes = getCasosValidacaoPendentesNaoAtribuidos();
        if (listaCasosValidacoesPendentes.isEmpty()) {
            return resUI;
        }

        //filtra por distrito
        if (!distrito.isEmpty()) {
            filtraCasosPorDistrito(distrito);
        }

        //ordena os casos do mais antigo para o mais recente
        ordenarCasosMaisAntigoParaMaisRecente();

        //constroi strings para apresentar
        Integer avaliacaoCaso;
        for (Caso caso : listaCasosValidacoesPendentes) {
            avaliacaoCaso = caso.getTotalAvaliacao();
            resUI.add(getStringUI(caso.toDTO()) + "Total Avaliação de Risco: " + avaliacaoCaso);
        }

        return resUI;
    }

    /**
     * Método que filtra a lista de casos com pedidos de validação pendentes
     * com base no distrito passado por parametro
     * Se houver pelo menos um objecto seguro que pertença ao distrito passado por parametro
     * considera-se o caso.
     *
     * @param distrito, String, com o nome do distrito
     */
    private void filtraCasosPorDistrito(String distrito) {
        String temp;
        Boolean filtro = false;
        List<Caso> listaRemove = new LinkedList<>();

        if (!this.listaCasosValidacoesPendentes.isEmpty()) {
            for (Caso c : this.listaCasosValidacoesPendentes) {
                for (ObjetoSeguro obj : c.objetosSegurados()) {
                    temp = LocationInfos.getDistrictFromCoordinates(obj.getLatitude(), obj.getLongitude());
                    if (temp != null) {
                        if (temp.equalsIgnoreCase(distrito)) {
                            filtro = true;
                        }
                    }
                }
                //Se não contem pelo menos um objecto seguro no distrito indicado remove o da lista
                if (filtro.equals(Boolean.FALSE)) {
                    listaRemove.add(c);
                }
                filtro = false;
            }
        }
        listaCasosValidacoesPendentes.removeAll(listaRemove);
    }

    /**
     * Método que ordena a lista de casos com base na data do pedido de validação,
     * ordenando-os do mais antigo para o mais recente
     */
    private void ordenarCasosMaisAntigoParaMaisRecente() {
        Collections.sort(listaCasosValidacoesPendentes);
    }

    /**
     * Método que devolve uma lista de casos com pedidos de validação de avaliação
     * de risco pendentes e ainda não atribuídos
     *
     * @return listaCasosValidacoesPendentes, List<Caso>, lista de casos
     */
    private List<Caso> getCasosValidacaoPendentesNaoAtribuidos() {
        List<Caso> listaCasosValidacoesPendentes = casoRepository.fetchCasesEmValidacaoNaoAtribuidos();

        return listaCasosValidacoesPendentes;
    }

    /**
     * Método que permite fazer atribuição do pedido de validação de avaliação de risco
     * de um caso a um analista de risco
     *
     * @param idCaso,       Long, id do caso a ser atribuido
     * @param nomeAnalista, String, nome do analista de risco a quem se vai atribuir o cso
     * @return Boolean.TRUE se for atribuido com sucesso ou Boolean.FALSE caso contrário
     */
    public Boolean atribuirCaso(Long idCaso, String nomeAnalista) {
        /*
         *       QD É FEITO O PEDIDO DE VALIDAÇÃO CRIAR DATA PEDIDO DE VALIDAÇÃO
         *
         * */
        User analistaRisco;
        analistaRisco = userRepository.findByTitle(nomeAnalista);
        userRepository.detach(analistaRisco);

        if (analistaRisco == null) {
            return false;
        }

        for (Caso c : listaCasosValidacoesPendentes) {
            if (c.getID().equals(idCaso)) {
                c.atribuirValidacao(analistaRisco);
                if (casoRepository.update(c) != null) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Método que devolve a String descritiva de cada caso a apresentar na UI
     *
     * @param casoDTO, CasoDTO, caso sobre o qual se quer construir a String descritiva
     * @return s.toString, String, string com a descrição do caso
     */
    private String getStringUI(CasoDTO casoDTO) {
        StringBuilder s = new StringBuilder();

        s.append("ID caso: ").append(casoDTO.getID()).append(", ").
            append("Estado caso: ").append(casoDTO.getEstado()).append(", ");

        return s.toString();
    }

}
