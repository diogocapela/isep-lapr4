package models;

import models.caracteristica.Contribuicao;
import models.caracteristica.Necessidade;
import models.caracteristica.Peso;
import models.celula.CelulaBase;
import models.celula.CelulaCaracterizada;
import models.celula.CelulaDetalhada;
import models.detalhe.Escala;
import models.matriz.Matriz;
import modelsDTO.CelulaBaseDTO;
import modelsDTO.MatrizDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

public class MatrizTest {

    ArrayList<CelulaBaseDTO> listaBaseDTO;
    private LinkedList<CelulaBase> listaCelulasBase;
    private LinkedList<CelulaBase> listaCelulasCaracterizada1;
    private LinkedList<CelulaBase> listaCelulasCaracterizada2;
    private LinkedList<CelulaDetalhada> listaCelulasDetalhada1;
    private LinkedList<CelulaDetalhada> listaCelulasDetalhada2;
    private CelulaBase celulaBase1;
    private CelulaBase celulaBase2;
    private CelulaCaracterizada celulaCaracterizada1;
    private CelulaCaracterizada celulaCaracterizada2;
    private CelulaCaracterizada celulaCaracterizada3;
    private CelulaDetalhada celulaDetalhada1;
    private CelulaDetalhada celulaDetalhada2;
    private CelulaDetalhada celulaDetalhada3;
    private Cobertura cobertura;
    private FatorRisco fatorRisco;
    private Envolvente envolvente;
    private Peso peso;
    private Contribuicao contribuicao;
    private Necessidade necessidade;
    private Escala escala;
    private Cobertura cobertura02;
    private Matriz matrizBase;
    private Matriz matrizCaracterizada;


    public MatrizTest() {
    }


    @Before
    public void setUp() {
        cobertura = new Cobertura("Incendio", "Seguro para incêndios");
        cobertura02 = new Cobertura("Incendio2", "Seguro para incendios");
        envolvente = new Envolvente("Policia", "Envolvente em Policia.", 1.1, 1.2);
        fatorRisco = new FatorRisco("Densidade", "Densidade de...");
        peso = Peso.criarPeso(3);
        contribuicao = Contribuicao.contribuicaoPositiva();
        necessidade = Necessidade.necessidadeObrigatoria();
        escala = Escala.criarEscala(1, 2, 3);

        celulaBase1 = new CelulaBase(cobertura, envolvente, fatorRisco);
        celulaBase2 = new CelulaBase(cobertura, envolvente, fatorRisco);

        celulaCaracterizada1 = new CelulaCaracterizada(peso, contribuicao, necessidade, cobertura, envolvente, fatorRisco);
        celulaCaracterizada2 = new CelulaCaracterizada(peso, contribuicao, necessidade, cobertura, envolvente, fatorRisco);
        celulaCaracterizada3 = new CelulaCaracterizada(peso, contribuicao, necessidade, cobertura02, envolvente, fatorRisco);

        celulaDetalhada1 = new CelulaDetalhada(escala, peso, contribuicao, necessidade, cobertura, envolvente, fatorRisco);
        celulaDetalhada2 = new CelulaDetalhada(escala, peso, contribuicao, necessidade, cobertura, envolvente, fatorRisco);
        celulaDetalhada3 = new CelulaDetalhada(escala, peso, contribuicao, necessidade, cobertura02, envolvente, fatorRisco);

        listaCelulasBase = new LinkedList<>();
        listaCelulasBase.add(celulaBase1);
        listaCelulasBase.add(celulaBase2);

        listaCelulasCaracterizada1 = new LinkedList<>();
        listaCelulasCaracterizada1.add(celulaCaracterizada1);
        listaCelulasCaracterizada1.add(celulaCaracterizada2);

        listaCelulasCaracterizada2 = new LinkedList<>();
        listaCelulasCaracterizada2.add(celulaCaracterizada1);
        listaCelulasCaracterizada2.add(celulaCaracterizada3);

        listaCelulasDetalhada1 = new LinkedList<>();
        listaCelulasDetalhada1.add(celulaDetalhada1);
        listaCelulasDetalhada1.add(celulaDetalhada2);

        listaCelulasDetalhada2 = new LinkedList<>();
        listaCelulasDetalhada2.add(celulaDetalhada1);
        listaCelulasDetalhada2.add(celulaDetalhada3);

        matrizBase = new Matriz(listaCelulasBase);
        matrizCaracterizada = new Matriz(listaCelulasCaracterizada1);


    }

    @After
    public void tearDown() {
    }

    @Test
    public void caracterizarMatrizTest() {
        Assert.assertFalse(matrizBase.isCaracterizada());
        Matriz matrizTest1 = matrizBase.caracterizarMatriz(listaCelulasCaracterizada1);
        Assert.assertTrue(matrizTest1.isCaracterizada());
        Matriz matrizTestNull = matrizTest1.caracterizarMatriz(listaCelulasCaracterizada1);
        Assert.assertEquals(null, matrizTestNull);
        Matriz matrizTest2 = matrizBase.caracterizarMatriz(listaCelulasCaracterizada2);
        Assert.assertEquals(null, matrizTest2);
        Matriz matrizTest3 = matrizBase.caracterizarMatriz(listaCelulasBase);
        Assert.assertEquals(null, matrizTest3);
    }

    @Test
    public void detalharMatrizTest() {
        Matriz matrizCaracterizada1 = matrizBase.caracterizarMatriz(listaCelulasCaracterizada1);
        Assert.assertFalse(matrizCaracterizada1.isDetalhada());

        Matriz matrizDetalhada1 = matrizCaracterizada1.detalharMatriz(listaCelulasDetalhada1);
        Assert.assertTrue(matrizDetalhada1.isDetalhada());

        Matriz matrizNull = matrizDetalhada1.detalharMatriz(listaCelulasDetalhada1);
        Assert.assertEquals(null, matrizNull);

        matrizNull = matrizCaracterizada1.detalharMatriz(listaCelulasDetalhada2);
        Assert.assertEquals(null, matrizNull);
    }

    @Test
    public void publicarMatrizTest() {
        Assert.assertFalse(matrizBase.publicarMatriz());
        Assert.assertFalse(matrizCaracterizada.publicarMatriz());

        Matriz matrizCaracterizada1 = matrizBase.caracterizarMatriz(listaCelulasCaracterizada1);
        Matriz matrizDetalhada1 = matrizCaracterizada1.detalharMatriz(listaCelulasDetalhada1);
        Assert.assertTrue(matrizDetalhada1.publicarMatriz());
    }

    @Test
    public void cancelarMatrizTest() {
        Assert.assertFalse(matrizBase.cancelarMatriz());
        Assert.assertFalse(matrizCaracterizada.cancelarMatriz());

        Matriz matrizCaracterizada1 = matrizBase.caracterizarMatriz(listaCelulasCaracterizada1);
        Matriz matrizDetalhada1 = matrizCaracterizada1.detalharMatriz(listaCelulasDetalhada1);

        Assert.assertFalse(matrizDetalhada1.cancelarMatriz());

        matrizDetalhada1.publicarMatriz();
        Assert.assertTrue(matrizDetalhada1.cancelarMatriz());
    }

    @Test
    public void rebasearMatrizTest() {

        Assert.assertNull(matrizBase.rebasearMatriz());

        Matriz matrizBase1 = matrizBase;
        Matriz matrizCaracterizada1 = matrizBase.caracterizarMatriz(listaCelulasCaracterizada1);

        //nao existe um .equals para MATRIZ, enquanto nao houver temos que testar assim
        Assert.assertNotNull(matrizCaracterizada1.rebasearMatriz());

        Matriz matrizDetalhada1 = matrizCaracterizada1.detalharMatriz(listaCelulasDetalhada1);
        Assert.assertNotNull(matrizDetalhada1.rebasearMatriz());

    }

    @Test
    public void isBaseTest() {
        Assert.assertTrue(matrizBase.isBase());

        Matriz matrizCaracterizada1 = matrizBase.caracterizarMatriz(listaCelulasCaracterizada1);
        Assert.assertFalse(matrizCaracterizada1.isBase());

        Matriz matrizDetalhada1 = matrizCaracterizada1.detalharMatriz(listaCelulasDetalhada1);
        Assert.assertFalse(matrizDetalhada1.isBase());
    }

    @Test
    public void isCaracterizadaTest() {
        Assert.assertFalse(matrizBase.isCaracterizada());

        Matriz matrizCaracterizada1 = matrizBase.caracterizarMatriz(listaCelulasCaracterizada1);
        Assert.assertTrue(matrizCaracterizada1.isCaracterizada());

        Matriz matrizDetalhada1 = matrizCaracterizada1.detalharMatriz(listaCelulasDetalhada1);
        Assert.assertFalse(matrizDetalhada1.isCaracterizada());
    }

    @Test
    public void isDetalhadaTest() {
        Assert.assertFalse(matrizBase.isDetalhada());

        Matriz matrizCaracterizada1 = matrizBase.caracterizarMatriz(listaCelulasCaracterizada1);
        Assert.assertFalse(matrizCaracterizada1.isDetalhada());

        Matriz matrizDetalhada1 = matrizCaracterizada1.detalharMatriz(listaCelulasDetalhada1);
        Assert.assertTrue(matrizDetalhada1.isDetalhada());
    }

    @Test
    public void isPublicadaTest() {
        Assert.assertFalse(matrizBase.isPublicada());

        Matriz matrizCaracterizada1 = matrizBase.caracterizarMatriz(listaCelulasCaracterizada1);
        Assert.assertFalse(matrizCaracterizada1.isPublicada());

        Matriz matrizDetalhada1 = matrizCaracterizada1.detalharMatriz(listaCelulasDetalhada1);
        Assert.assertFalse(matrizDetalhada1.isPublicada());

        matrizDetalhada1.publicarMatriz();
        Assert.assertTrue(matrizDetalhada1.isPublicada());
    }

    @Test
    public void toDTOTest() {
        //teste matriz base
        LinkedList<CelulaBaseDTO> listaBaseDTO = new LinkedList<>();
        for (CelulaBase cel : listaCelulasBase) {

            listaBaseDTO.add(cel.toBaseDTO());
        }
        MatrizDTO matrizExpectedBaseDTO = new MatrizDTO("base", listaBaseDTO, null);
        MatrizDTO matrizActualBaseDTO = matrizBase.toDTO();

        for (int i = 0; i < matrizActualBaseDTO.getCelulas().size(); i++) {
            Assert.assertEquals(matrizActualBaseDTO.getCelulas().get(i).getCoberturaDTO(),
                matrizExpectedBaseDTO.getCelulas().get(i).getCoberturaDTO());

            Assert.assertEquals(matrizActualBaseDTO.getCelulas().get(i).getFatorRiscoDTO(),
                matrizExpectedBaseDTO.getCelulas().get(i).getFatorRiscoDTO());

            Assert.assertEquals(matrizActualBaseDTO.getCelulas().get(i).getEnvolventeDTO(),
                matrizExpectedBaseDTO.getCelulas().get(i).getEnvolventeDTO());
        }

        Assert.assertEquals(matrizActualBaseDTO.getEstadoMatriz(),
            matrizExpectedBaseDTO.getEstadoMatriz());

        //teste matriz caracterizada
        LinkedList<CelulaBaseDTO> listaCaracterizadaDTO = new LinkedList<>();
        for (CelulaBase cel : listaCelulasCaracterizada1) {
            listaCaracterizadaDTO.add(cel.toBaseDTO());
        }
        Matriz matrizCaracterizada123 = matrizBase.caracterizarMatriz(listaCelulasCaracterizada1);
        MatrizDTO matrizExpectedCaracterizadaDTO = new MatrizDTO("caracterizada", listaCaracterizadaDTO, null);

        MatrizDTO matrizActualCaracterizadaDTO = matrizCaracterizada123.toDTO();

        for (int i = 0; i < matrizActualCaracterizadaDTO.getCelulas().size(); i++) {
            Assert.assertEquals(matrizActualCaracterizadaDTO.getCelulas().get(i).getCoberturaDTO(),
                matrizExpectedCaracterizadaDTO.getCelulas().get(i).getCoberturaDTO());

            Assert.assertEquals(matrizActualCaracterizadaDTO.getCelulas().get(i).getFatorRiscoDTO(),
                matrizExpectedCaracterizadaDTO.getCelulas().get(i).getFatorRiscoDTO());

            Assert.assertEquals(matrizActualCaracterizadaDTO.getCelulas().get(i).getEnvolventeDTO(),
                matrizExpectedCaracterizadaDTO.getCelulas().get(i).getEnvolventeDTO());

            Assert.assertEquals(matrizActualCaracterizadaDTO.getCelulas().get(i).getEnvolventeDTO(),
                matrizExpectedCaracterizadaDTO.getCelulas().get(i).getEnvolventeDTO());
        }

        Assert.assertEquals(matrizActualCaracterizadaDTO.getEstadoMatriz(),
            matrizExpectedCaracterizadaDTO.getEstadoMatriz());

        //teste matriz caracterizada
        LinkedList<CelulaBaseDTO> listaDetalhadaDTO = new LinkedList<>();
        for (CelulaBase cel : listaCelulasDetalhada1) {
            listaDetalhadaDTO.add(cel.toBaseDTO());
        }
        Matriz matrizDetalhada123 = matrizBase.detalharMatriz(listaCelulasDetalhada1);
        MatrizDTO matrizExpectedDetalhadaDTO = new MatrizDTO("detalhada", listaDetalhadaDTO, null);

        MatrizDTO matrizActualDetalhadaDTO = matrizCaracterizada123.toDTO();

        for (int i = 0; i < matrizActualDetalhadaDTO.getCelulas().size(); i++) {
            Assert.assertEquals(matrizActualDetalhadaDTO.getCelulas().get(i).getCoberturaDTO(),
                matrizExpectedDetalhadaDTO.getCelulas().get(i).getCoberturaDTO());

            Assert.assertEquals(matrizActualDetalhadaDTO.getCelulas().get(i).getFatorRiscoDTO(),
                matrizExpectedDetalhadaDTO.getCelulas().get(i).getFatorRiscoDTO());

            Assert.assertEquals(matrizActualDetalhadaDTO.getCelulas().get(i).getEnvolventeDTO(),
                matrizExpectedDetalhadaDTO.getCelulas().get(i).getEnvolventeDTO());

            Assert.assertEquals(matrizActualCaracterizadaDTO.getCelulas().get(i).getEnvolventeDTO(),
                matrizExpectedDetalhadaDTO.getCelulas().get(i).getEnvolventeDTO());
        }

        Assert.assertEquals(matrizActualDetalhadaDTO.getEstadoMatriz(),
            matrizExpectedDetalhadaDTO.getEstadoMatriz());
    }
}
