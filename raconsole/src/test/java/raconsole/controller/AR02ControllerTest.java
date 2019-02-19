package raconsole.controller;

import models.avaliacao.Caso;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Test;
import repository.CasoRepository;

import java.util.LinkedList;
import java.util.List;

public class AR02ControllerTest {

    AR02Controller controller =
        new AR02Controller();
    CasoRepository casoRepository = new CasoRepository();

    public AR02ControllerTest() {
    }

    @Test
    public void mostarApenasCasosComPedidosValidacaoPendentesNaoAtribuidos() {
        Caso caso;
        String distrito = "";
        List<String> casosValidacaoPendente = controller.getCasosEmValidacaoNaoAtribuidos(distrito);
        for (String c : casosValidacaoPendente) {
            String temp[] = c.split(",");
            String tempId[] = temp[0].split("ID caso: ");
            Long id = Long.parseLong(tempId[1]);
            caso = casoRepository.findById(id);
            Assert.isTrue(caso.isEmValidacao(), "O ESTADO do caso devia ser VALIDACAO.");
            Assert.isFalse(caso.isValidacaoAtribuida(), "A VALIDAÇÃO do caso não devia estar atribuída.");
        }
    }

    @Test
    public void mostrarCasosComPeloMenosUmObjectoSeguroDeDistrito() {
        String distrito = "porto";
        String userName = "Teresa";
        Caso caso;
        List<String> casosValidacaoPendente = controller.getCasosEmValidacaoNaoAtribuidos(distrito);

        Assert.isTrue(casosValidacaoPendente.isEmpty(), "A lista devia estar vazia pq não " +
            "sabemos o distrito do caso( random coordinates, para já)" + casosValidacaoPendente.size());

        /*
        QUANDO TIVERMOS UM CASO EM QUE SABEMOS O DISTRITO

        Boolean hasDistrito = false;

        for(String c: casosValidacaoPendente){
            String temp[] = c.split(",");
            String tempId[] = temp[0].split("ID caso: ");
            Long id = Long.parseLong(tempId[1]);
            caso = casoRepository.findById(id);
            for (ObjetoSeguro obj : caso.objetosSegurados()) {
                if (LocationInfos.getDistrictFromCoordinates(obj.getLatitude(), obj.getLongitude()).equalsIgnoreCase(distrito)) {
                    hasDistrito = true;
                }
            }

            Assert.isFalse(hasDistrito.equals(Boolean.TRUE), "O caso devia ter pelo menos um objecto segurado pertencente ao distrito dado.");
            hasDistrito = false;

        }
         */
    }

    @Test
    public void mostrarCasosComOrdenacaoDescendente() {
        String distrito = "";
        List<String> casosValidacaoPendente = controller.getCasosEmValidacaoNaoAtribuidos(distrito);

        if (casosValidacaoPendente.size() > 1) {
            List<Caso> listaCasos = new LinkedList<>();
            Caso caso;

            for (String c : casosValidacaoPendente) {
                String temp[] = c.split(",");
                String tempId[] = temp[0].split("ID caso: ");
                Long id = Long.parseLong(tempId[1]);
                caso = casoRepository.findById(id);
                listaCasos.add(caso);
            }

            String casoA = casosValidacaoPendente.get(0);
            String casoB = casosValidacaoPendente.get(1);

            Assert.isTrue(casoA.compareTo(casoB) < 0, "Caso a devia ser mais antigo do que b!");
        }

    }

    @Test
    public void mostrarCasosComOrdenacaoDescendenteErrado() {
        String distrito = "";
        List<String> casosValidacaoPendente = controller.getCasosEmValidacaoNaoAtribuidos(distrito);

        if (casosValidacaoPendente.size() > 1) {
            List<Caso> listaCasos = new LinkedList<>();
            Caso caso;

            for (String c : casosValidacaoPendente) {
                String temp[] = c.split(",");
                String tempId[] = temp[0].split("ID caso: ");
                Long id = Long.parseLong(tempId[1]);
                caso = casoRepository.findById(id);
                listaCasos.add(caso);
            }

            String casoA = casosValidacaoPendente.get(0);
            String casoB = casosValidacaoPendente.get(1);

            Assert.isFalse(casoB.compareTo(casoA) < 0, "Caso b devia ser mais recente do que a!");
        }

    }


}
