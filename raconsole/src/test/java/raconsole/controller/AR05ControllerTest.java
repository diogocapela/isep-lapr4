package raconsole.controller;


import models.User;
import models.avaliacao.Caso;
import models.matriz.Matriz;
import modelsDTO.CasoDTO;
import org.junit.Before;
import org.junit.Test;
import repository.CasoRepository;
import repository.MatrizRepository;
import repository.UserRepository;
import services.ProcessCasoService;
import utils.XMLImporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.*;

public class AR05ControllerTest {

    private static final String nomeAnalista = "Teresa";
    private static String xmlValidacaoSim = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <caso> <objetos_segurados> <objeto_seguro> <descricao>a</descricao>  " +
        "    <lat>2.0</lat> <lng>3.2</lng><coberturas> <cobertura> <titulo_cobertura>ccc</titulo_cobertura> <descricao_cobertura>cccc</descricao_cobertura>\n" +
        "                </cobertura> <cobertura>    <titulo_cobertura>c</titulo_cobertura> <descricao_cobertura>cc</descricao_cobertura>\n" +
        "                </cobertura> </coberturas> </objeto_seguro> <objeto_seguro> <descricao>b</descricao>  <lat>4</lat>  <lng>5.5</lng>\n" +
        "            <coberturas>  <cobertura>  <titulo_cobertura>c</titulo_cobertura>  <descricao_cobertura>cc</descricao_cobertura> </cobertura>\n" +
        "            </coberturas>  </objeto_seguro> </objetos_segurados>\n" +
        "    <validacao>sim</validacao> </caso>";
    private final XMLImporter xmlImporter = new XMLImporter();
    User user;
    AR05Controller ar05controller = new AR05Controller();
    CasoRepository casoRepository = new CasoRepository();
    UserRepository userRepository = new UserRepository();


    @Before
    public void setUp() {
        user = userRepository.findByTitle(nomeAnalista);
    }

    @Test
    public void ensureGetCasosValidadosPorAnalistaWorks() {
        // criar caso para testar
        //IMPORTAR
        Caso caso = null;
        try {
            caso = xmlImporter.importCasoFromString(xmlValidacaoSim);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //return null;
        }
        ProcessCasoService processCasoService = new ProcessCasoService();
        MatrizRepository matrizRepository = new MatrizRepository();
        Matriz mp = matrizRepository.getMatrizPublicada();
        Caso casoReal = processCasoService.processarCaso(caso);

        // confirmar que não surge no output antes de validado
        List<String> antes = ar05controller.getCasosValidadosPorAnalista(nomeAnalista);
        for (String str : antes) {
            assertFalse(str.contains("id: " + casoReal.getID()));
        }

        if (mp == null) {
            assertNull(casoReal);
        } else {
            assertNotNull(casoReal.toDTO().getValidacaoDTO().getDataPedido());
            assertNull(casoReal.toDTO().getValidacaoDTO().getAnalistaNome());
            assertNull(casoReal.toDTO().getValidacaoDTO().getAnalistaEmail());
            assertNull(casoReal.toDTO().getValidacaoDTO().getDataAtribuicao());
            assertNull(casoReal.toDTO().getValidacaoDTO().getDataConclusao());
            // correr processo de avaliar
            casoReal.atribuirValidacao(user);
            casoReal.processado();
            casoRepository.update(casoReal);
            casoReal = casoRepository.findById(casoReal.getID());
            // confirmar que surge no output antes de validado
            boolean found = false;
            List<String> depois = ar05controller.getCasosValidadosPorAnalista(nomeAnalista);
            for (String str : depois) {
                found = found || str.contains("id: " + casoReal.getID());
            }
            assertTrue(found);
        }
    }

    @Test
    public void testeOrdenarPedidosMaisRecentes() {
        List<Caso> casosAnalista = casoRepository.fetchCasosValidadosPorAnalista(nomeAnalista);
        List<CasoDTO> casosDTOSAnalistaOrdenados = new ArrayList<>();
        Collections.sort(casosAnalista);
        Collections.reverse(casosAnalista);
        for (Caso caso : casosAnalista) {
            casosDTOSAnalistaOrdenados.add(caso.toDTO());
        }

        List<CasoDTO> actual = ar05controller.ordenarPedidosMaisRecentes(nomeAnalista);

        assertEquals(casosDTOSAnalistaOrdenados.size(), actual.size());

        for (int i = 0; i < casosDTOSAnalistaOrdenados.size(); i++) {
            assertEquals(casosDTOSAnalistaOrdenados.get(i), actual.get(i));
        }

        //assertEquals(casosDTOSAnalistaOrdenados, ar05controller.ordenarPedidosMaisRecentes(nomeAnalista));
    }


    @Test
    public void testGetTempoMedioAnaliseComDTO() {
        List<Caso> casosAnalista = casoRepository.fetchCasosValidadosPorAnalista(nomeAnalista);
        List<CasoDTO> casosDTOS = new ArrayList<>();
        Long soma = 0L;
        Long media = 0L;
        for (Caso caso : casosAnalista) {
            soma += caso.tempoOcorridoEntreDataConclusaoEDataAtribuido();
        }
        if (!casosAnalista.isEmpty()) {
            media = soma / casosAnalista.size();
        }
        for (Caso caso : casosAnalista) {
            casosDTOS.add(caso.toDTO());
        }
        assertEquals(media, ar05controller.getTempoMedioAnaliseComDTO(casosDTOS));
    }

    @Test
    public void testGetTempoMedioAnalise() {
        List<Caso> casosAnalista = casoRepository.fetchCasosValidadosPorAnalista(nomeAnalista);
        Long soma = 0L;
        Long media = 0L;
        for (Caso caso : casosAnalista) {
            soma += caso.tempoOcorridoEntreDataConclusaoEDataAtribuido();
        }
        if (!casosAnalista.isEmpty()) {
            media = soma / casosAnalista.size();
        }
        assertEquals(media, ar05controller.getTempoMedioAnalise(casosAnalista));
    }

    @Test
    public void testFiltrarCasosPeriodoTempo() {
        List<Caso> casosAnalista = casoRepository.fetchCasosValidadosPorAnalista(nomeAnalista);
        List<CasoDTO> casosDTOSAnalista = new ArrayList<>();
        List<CasoDTO> casosDtos = new ArrayList<>();
        Integer ano = 2019;
        Integer mesInicial = 05;
        Integer diaInicial = 1;
        Integer mesFinal = 06;
        Integer diaFinal = 31;
        Date dataInicial = new Date();
        //dataInicial.setYear(ano);
        dataInicial.setMonth(mesInicial);
        dataInicial.setDate(diaInicial);
        Date dataFinal = new Date();
        //dataFinal.setYear(ano);
        dataFinal.setMonth(mesFinal);
        dataFinal.setDate(diaFinal);
        for (Caso caso : casosAnalista) {
            if (caso.getDataConclusao().compareTo(dataInicial) > 0 && caso.getDataConclusao().compareTo(dataFinal) < 0) {
                casosDtos.add(caso.toDTO());
            }
        }
        for (Caso caso : casosAnalista) {
            casosDTOSAnalista.add(caso.toDTO());
        }
        assertEquals(casosDtos, ar05controller.filtrarCasosPeriodoTempo(ano, mesInicial, diaInicial, mesFinal, diaFinal, casosDTOSAnalista));
    }
}



