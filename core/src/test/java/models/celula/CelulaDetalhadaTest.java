/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.celula;

import models.Cobertura;
import models.Envolvente;
import models.FatorRisco;
import models.caracteristica.Contribuicao;
import models.caracteristica.Necessidade;
import models.caracteristica.Peso;
import models.detalhe.Escala;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class CelulaDetalhadaTest {
    private CelulaBase celulaBase;
    private CelulaCaracterizada celulaCaracterizada;
    private CelulaDetalhada celulaDetalhada;
    private Cobertura cobertura;
    private FatorRisco fatorRisco;
    private Envolvente envolvente;
    private Peso peso;
    private Contribuicao contribuicao;
    private Necessidade necessidade;
    private Escala escala;
    private Cobertura cobertura02;

    public CelulaDetalhadaTest() {
    }


    @Before
    public void setUp() {
        cobertura = new Cobertura("Incendio", "Seguro para incêndios");
        envolvente = new Envolvente("Policia", "Envolvente em Policia.", 1.1, 1.2);
        fatorRisco = new FatorRisco("Densidade", "Densidade de...");
        peso = Peso.criarPeso(3);
        contribuicao = Contribuicao.contribuicaoPositiva();
        necessidade = Necessidade.necessidadeObrigatoria();
        escala = Escala.criarEscala(1, 3, 5);
        cobertura02 = new Cobertura("Incendio2", "Seguro para incendios");


        celulaBase = new CelulaBase(cobertura, envolvente, fatorRisco);
        celulaCaracterizada = new CelulaCaracterizada(peso, contribuicao, necessidade, cobertura, envolvente, fatorRisco);
        celulaDetalhada = new CelulaDetalhada(escala, peso, contribuicao, necessidade, cobertura, envolvente, fatorRisco);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of exportCelula method, of class CelulaDetalhada.
     */
    @Test
    public void testExportCelula() {
        System.out.println("exportCelula");
        CelulaDetalhada instance = celulaDetalhada;
        String[] expResult = celulaDetalhada.toExporterDTO().get(0);
        String[] result = instance.toExporterDTO().get(0);
        assertArrayEquals(expResult, result);

    }

    /**
     * Test of match method, of class CelulaDetalhada.
     */
    @Test
    public void testMatchBetweenCelulasDetalhadas() {
        System.out.println("match");
        Celula c = celulaDetalhada;
        CelulaDetalhada instance = new CelulaDetalhada(escala, peso, contribuicao, necessidade, cobertura, envolvente, fatorRisco);
        boolean expResult = true;
        boolean result = instance.match(c);
        assertEquals(expResult, result);
    }

    /**
     * Test of no match method, of class CelulaDetalhada.
     */
    @Test
    public void testNoMatchBetweenCelulasDetalhadas() {
        System.out.println("no match");
        Celula c = celulaDetalhada;
        CelulaDetalhada instance = new CelulaDetalhada(escala, peso, contribuicao, necessidade, cobertura02, envolvente, fatorRisco);
        boolean expResult = false;
        boolean result = instance.match(c);
        assertEquals(expResult, result);
    }


}
