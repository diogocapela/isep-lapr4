package raconsole.controller;

import models.avaliacao.Caso;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Test;
import repository.CasoRepository;

import java.util.List;


public class AR03ControllerTest {


    private static final String nomeAnalista = "Rui";
    AR03Controller ar03Controller = new AR03Controller();
    private CasoRepository casoRepository = new CasoRepository();

    @Test
    public void testgetPedidosdeAvaliacaodeRiscopendentes() {
        Caso caso;
        List<String> lista = ar03Controller.getPedidosdeAvaliacaodeRiscopendentes(nomeAnalista);
        for (String c : lista) {
            String temp[] = c.split(",");
            String tempId[] = temp[0].split("ID caso: ");
            Long id = Long.parseLong(tempId[1]);
            caso = casoRepository.findById(id);
            Assert.isTrue(caso.isValidacaoAtribuida(), "O ESTADO do caso esta atribuido");

        }

        Caso caso1;
        List<String> lista1 = ar03Controller.getPedidosdeAvaliacaodeRiscopendentes("");
        for (String c1 : lista1) {
            String temp1[] = c1.split(",");
            String tempId1[] = temp1[0].split("ID caso: ");
            Long id1 = Long.parseLong(tempId1[1]);
            caso1 = casoRepository.findById(id1);
            Assert.isFalse(caso1.isValidacaoAtribuida(), "Sem casos atribuidos");

        }
    }
}
