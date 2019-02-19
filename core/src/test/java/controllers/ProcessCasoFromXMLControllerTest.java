package controllers;

import models.matriz.Matriz;
import org.junit.Assert;
import org.junit.Test;
import repository.MatrizRepository;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessCasoFromXMLControllerTest {

    private static String xmlValidacaoSim = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <caso> <objetos_segurados> <objeto_seguro> <descricao>a</descricao>  " +
        "    <lat>2.0</lat> <lng>3.2</lng><coberturas> <cobertura> <titulo_cobertura>ccc</titulo_cobertura> <descricao_cobertura>cccc</descricao_cobertura>\n" +
        "                </cobertura> <cobertura>    <titulo_cobertura>c</titulo_cobertura> <descricao_cobertura>cc</descricao_cobertura>\n" +
        "                </cobertura> </coberturas> </objeto_seguro> <objeto_seguro> <descricao>b</descricao>  <lat>4</lat>  <lng>5.5</lng>\n" +
        "            <coberturas>  <cobertura>  <titulo_cobertura>c</titulo_cobertura>  <descricao_cobertura>cc</descricao_cobertura> </cobertura>\n" +
        "            </coberturas>  </objeto_seguro> </objetos_segurados>\n" +
        "    <validacao>sim</validacao> </caso>";

    private static String xmlValidacaoNaoSim = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <caso> <objetos_segurados> <objeto_seguro> <descricao>a</descricao>  " +
        "    <lat>2.0</lat> <lng>3.2</lng><coberturas> <cobertura> <titulo_cobertura>ccc</titulo_cobertura> <descricao_cobertura>cccc</descricao_cobertura>\n" +
        "                </cobertura> <cobertura>    <titulo_cobertura>c</titulo_cobertura> <descricao_cobertura>cc</descricao_cobertura>\n" +
        "                </cobertura> </coberturas> </objeto_seguro> <objeto_seguro> <descricao>b</descricao>  <lat>4</lat>  <lng>5.5</lng>\n" +
        "            <coberturas>  <cobertura>  <titulo_cobertura>c</titulo_cobertura>  <descricao_cobertura>cc</descricao_cobertura> </cobertura>\n" +
        "            </coberturas>  </objeto_seguro> </objetos_segurados>\n" +
        "    <validacao>n</validacao> </caso>";

    private static String xmlValidacaoNull = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <caso> <objetos_segurados> <objeto_seguro> <descricao>a</descricao>  " +
        "    <lat>2.0</lat> <lng>3.2</lng><coberturas> <cobertura> <titulo_cobertura>ccc</titulo_cobertura> <descricao_cobertura>cccc</descricao_cobertura>\n" +
        "                </cobertura> <cobertura>    <titulo_cobertura>c</titulo_cobertura> <descricao_cobertura>cc</descricao_cobertura>\n" +
        "                </cobertura> </coberturas> </objeto_seguro> <objeto_seguro> <descricao>b</descricao>  <lat>4</lat>  <lng>5.5</lng>\n" +
        "            <coberturas>  <cobertura>  <titulo_cobertura>c</titulo_cobertura>  <descricao_cobertura>cc</descricao_cobertura> </cobertura>\n" +
        "            </coberturas>  </objeto_seguro> </objetos_segurados> </caso>";

    ProcessCasoFromXMLController controller = new ProcessCasoFromXMLController();

    @Test
    public void ensureEmptyInputStringReturnsNullResult() {
        Assert.assertNull(controller.processarCaso(""));
    }

    @Test
    public void ensureNullInputStringReturnsNullResult() {
        Assert.assertNull(controller.processarCaso(null));
    }

    @Test
    public void ensureUnformattedInputStringReturnsNullResult() {
        Assert.assertNull(controller.processarCaso("<a>b</a>"));
    }

    @Test
    public void ensureFormattedInputStringReturnsNonNullResult() {
        MatrizRepository matrizRepository = new MatrizRepository();
        Matriz mp = matrizRepository.getMatrizPublicada();
        if (mp != null) {
            assertNotEquals(null, controller.processarCaso(xmlValidacaoSim));
            assertNotEquals(null, controller.processarCaso(xmlValidacaoNaoSim));
            assertNotEquals(null, controller.processarCaso(xmlValidacaoNull));
        }
    }

    @Test
    public void ensureCasoWithValidacaoIsEmValidacao() {
        MatrizRepository matrizRepository = new MatrizRepository();
        Matriz mp = matrizRepository.getMatrizPublicada();
        if (mp != null) {
            assertTrue(controller.processarCaso(xmlValidacaoSim).contains("validacão"));
        } else {
            assertTrue(controller.processarCaso(xmlValidacaoSim).contains("por avaliar"));
        }
    }

    @Test
    public void ensureCasoWithoutValidacaoIsProcessado() {
        MatrizRepository matrizRepository = new MatrizRepository();
        Matriz mp = matrizRepository.getMatrizPublicada();
        if (mp != null) {
            assertTrue(controller.processarCaso(xmlValidacaoNaoSim).contains("processado"));
            assertTrue(controller.processarCaso(xmlValidacaoNull).contains("processado"));
        } else {
            assertTrue(controller.processarCaso(xmlValidacaoNaoSim).contains("por avaliar"));
            assertTrue(controller.processarCaso(xmlValidacaoNull).contains("por avaliar"));
        }
    }
}
