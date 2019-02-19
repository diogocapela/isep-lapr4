package controllers;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CompararAvaliacaoDeObjetoFromXMLControllerTest {

    CompararAvaliacaoDeObjectoFromXMLController controller =
        new CompararAvaliacaoDeObjectoFromXMLController();
    private String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><comparacao><objeto_seguro><descricao>" +
        "Objecto da Travessa de Cedofeita</descricao><lat>41.150098</lat><lng>-8.614949</lng>" +
        "<coberturas><cobertura><titulo_cobertura>Incendio</titulo_cobertura>" +
        "<descricao_cobertura>Seguro para incêncios</descricao_cobertura></cobertura>" +
        "<cobertura><titulo_cobertura>Inundacao</titulo_cobertura>" +
        "<descricao_cobertura>Seguro para inundações</descricao_cobertura></cobertura>" +
        "<cobertura><titulo_cobertura>Tempestade</titulo_cobertura>" +
        "<descricao_cobertura>Seguro para tempestades</descricao_cobertura></cobertura></coberturas></objeto_seguro>" +
        "<matrizes><matrizIdA>53</matrizIdA><matrizIdB>72</matrizIdB></matrizes></comparacao>\n";
    private String xmlMalFormado = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><comparacao><objeto_seguro><descricao>" +
        "Objecto da Travessa de Cedofeita</descricao><lat>41.150098</lat><lng>-8.614949</lng>" +
        "<matrizes><matrizIdA>53</matrizIdA><matrizIdB>72</matrizIdB></matrizes></comparacao>" +
        "<coberturas><cobertura><titulo_cobertura>Incendio</titulo_cobertura>" +
        "<descricao_cobertura>Seguro para incêncios</descricao_cobertura></cobertura>" +
        "<cobertura><titulo_cobertura>Inundacao</titulo_cobertura>" +
        "<descricao_cobertura>Seguro para inundações</descricao_cobertura></cobertura>" +
        "<cobertura><titulo_cobertura>Tempestade</titulo_cobertura>" +
        "<descricao_cobertura>Seguro para tempestades</descricao_cobertura></cobertura></coberturas></objeto_seguro>";
    private String xmlStringSemCoberturas = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><comparacao><objeto_seguro><descricao>" +
        "Objecto da Travessa de Cedofeita</descricao><lat>41.150098</lat><lng>-8.614949</lng>" +
        "<coberturas></cobertura></coberturas></objeto_seguro>" +
        "<matrizes><matrizIdA>53</matrizIdA><matrizIdB>72</matrizIdB></matrizes></comparacao>\n";
    private String xmlStringIdsIncorrectos = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><comparacao><objeto_seguro><descricao>" +
        "Objecto da Travessa de Cedofeita</descricao><lat>41.150098</lat><lng>-8.614949</lng>" +
        "<coberturas><cobertura><titulo_cobertura>Incendio</titulo_cobertura>" +
        "<descricao_cobertura>Seguro para incêncios</descricao_cobertura></cobertura>" +
        "<cobertura><titulo_cobertura>Inundacao</titulo_cobertura>" +
        "<descricao_cobertura>Seguro para inundações</descricao_cobertura></cobertura>" +
        "<cobertura><titulo_cobertura>Tempestade</titulo_cobertura>" +
        "<descricao_cobertura>Seguro para tempestades</descricao_cobertura></cobertura></coberturas></objeto_seguro>" +
        "<matrizes><matrizIdA>-1</matrizIdA><matrizIdB>a</matrizIdB></matrizes></comparacao>\n";
    private String xmlStringIdsEmpty = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><comparacao><objeto_seguro><descricao>" +
        "Objecto da Travessa de Cedofeita</descricao><lat>41.150098</lat><lng>-8.614949</lng>" +
        "<coberturas><cobertura><titulo_cobertura>Incendio</titulo_cobertura>" +
        "<descricao_cobertura>Seguro para incêncios</descricao_cobertura></cobertura>" +
        "<cobertura><titulo_cobertura>Inundacao</titulo_cobertura>" +
        "<descricao_cobertura>Seguro para inundações</descricao_cobertura></cobertura>" +
        "<cobertura><titulo_cobertura>Tempestade</titulo_cobertura>" +
        "<descricao_cobertura>Seguro para tempestades</descricao_cobertura></cobertura></coberturas></objeto_seguro>" +
        "<matrizes><matrizIdA></matrizIdA><matrizIdB></matrizIdB></matrizes></comparacao>\n";

    @Test
    public void garantirQueDevolveNullComStringNull() {
        List<String> message = controller.comparaAvaliacao(null);
        Assert.assertTrue("A message devia ser null", message == null);
    }

    @Test
    public void garantirQueDevolveNullComStringEmpty() {
        List<String> message = controller.comparaAvaliacao("");
        Assert.assertTrue("A message devia ser null", message == null);
    }

    @Test
    public void garantirQueDevolveNullComStringXMLMalFormada() {
        List<String> message = controller.comparaAvaliacao(xmlMalFormado);
        Assert.assertTrue("A message devia ser null", message == null);
    }

    @Test
    public void garantirQueDevolveListaMensagemLength10ComDadosCorretos() {
        List<String> message = controller.comparaAvaliacao(xmlString);
        Assert.assertTrue("O tamanho da lista devia ser 10.", message.size() == 10);
    }

    @Test
    public void garantirQueDevolveListaMensagemNullComDadosInCorretos() {
        List<String> message = controller.comparaAvaliacao(xmlStringIdsIncorrectos);
        Assert.assertTrue("A message devia estar a null", message == null);
    }

    @Test
    public void garantirQueDevolveListaMensagemNullComMatrizesIdEmpty() {
        List<String> message = controller.comparaAvaliacao(xmlStringIdsEmpty);
        Assert.assertTrue("A message devia estar a null", message == null);
    }

    @Test
    public void garantirQueDevolveListaMensagemNullSemCoberturas() {
        List<String> message = controller.comparaAvaliacao(xmlStringSemCoberturas);
        Assert.assertTrue("A message devia estar a null", message == null);
    }

}
