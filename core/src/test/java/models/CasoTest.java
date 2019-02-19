package models;

import models.avaliacao.Caso;
import models.avaliacao.EstadoCaso;
import models.avaliacao.ObjetoSeguro;
import modelsDTO.CasoDTO;
import modelsDTO.ObjetoSeguroDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CasoTest {
    Caso caso1;
    Caso caso2;
    Caso caso3;
    Caso caso4;
    ObjetoSeguro objetoSeguro1;
    ObjetoSeguro objetoSeguro2;
    List<ObjetoSeguro> listObjetos1 = new ArrayList<>();
    Cobertura cobertura1;
    Cobertura cobertura2;
    Cobertura cobertura3;
    List<Cobertura> listaCoberturas1 = new ArrayList<>();
    List<Cobertura> listaCoberturas2 = new ArrayList<>();

    public CasoTest() {
    }


    @Before
    public void setUp() {
        cobertura1 = new Cobertura("titulo1", "descricao1");
        cobertura2 = new Cobertura("titulo2", "descricao2");
        cobertura3 = new Cobertura("titulo3", "descricao3");
        listaCoberturas1.add(cobertura1);
        listaCoberturas1.add(cobertura2);
        listaCoberturas2.add(cobertura1);
        listaCoberturas2.add(cobertura3);
        objetoSeguro1 = new ObjetoSeguro("objeto1", 10.0, 10.0, listaCoberturas1);
        objetoSeguro2 = new ObjetoSeguro("objeto2", 10.0, 10.0, listaCoberturas2);
        listObjetos1.add(objetoSeguro1);
        listObjetos1.add(objetoSeguro2);
        caso1 = new Caso(listObjetos1, EstadoCaso.ESPERA);
        caso2 = new Caso(listObjetos1, EstadoCaso.PROCESSADO);
        caso3 = new Caso(listObjetos1, EstadoCaso.PROCESSAMENTO);
        caso4 = new Caso(listObjetos1, EstadoCaso.VALIDACAO);
    }

    @Test
    public void isEmEsperaTest() {
        Assert.assertTrue(caso1.isEmEspera());
        Assert.assertFalse(caso2.isEmEspera());
    }

    @Test
    public void isEmProcessamentoTest() {
        Assert.assertTrue(caso3.isEmProcessamento());
        Assert.assertFalse(caso2.isEmProcessamento());
    }

    @Test
    public void isProcessadoTest() {
        Assert.assertTrue(caso2.isProcessado());
        Assert.assertFalse(caso1.isProcessado());
    }


    @Test
    public void toDTOTest() {
        List<ObjetoSeguroDTO> objetosSegurosExpectedDTO = new LinkedList<>();

        for (ObjetoSeguro os : listObjetos1) {
            objetosSegurosExpectedDTO.add(os.toDTO());
        }
        CasoDTO casoExpected = new CasoDTO(objetosSegurosExpectedDTO, EstadoCaso.ESPERA.toString(), 1L, null);

        CasoDTO casoAtual = caso1.toDTO();

        for (int i = 0; i < casoAtual.getObjetosSegurados().size(); i++) {
            Assert.assertEquals(casoExpected.getObjetosSegurados().get(i).getLatitude(),
                casoAtual.getObjetosSegurados().get(i).getLatitude());
            Assert.assertEquals(casoExpected.getObjetosSegurados().get(i).getLongitude(),
                casoAtual.getObjetosSegurados().get(i).getLongitude());
            Assert.assertEquals(casoExpected.getObjetosSegurados().get(i).getAvaliacao(),
                casoAtual.getObjetosSegurados().get(i).getAvaliacao());
            Assert.assertEquals(casoExpected.getObjetosSegurados().get(i).getCoberturas(),
                casoAtual.getObjetosSegurados().get(i).getCoberturas());
            Assert.assertEquals(casoExpected.getObjetosSegurados().get(i).getDescricao(),
                casoAtual.getObjetosSegurados().get(i).getDescricao());
        }
        Assert.assertEquals(casoExpected.getEstado().toString(), casoAtual.getEstado().toString());
    }


}
