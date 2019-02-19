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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class CelulaCaracterizadaTest {
    private CelulaBase celulaBase;
    private CelulaCaracterizada celulaCaracterizada;
    private Cobertura cobertura;
    private FatorRisco fatorRisco;
    private Envolvente envolvente;
    private Peso peso;
    private Contribuicao contribuicao;
    private Necessidade necessidade;
    private Cobertura cobertura02;


    public CelulaCaracterizadaTest() {
    }


    @Before
    public void setUp() {
        cobertura = new Cobertura("Incendio", "Seguro para incêndios");
        envolvente = new Envolvente("Policia", "Envolvente em Policia.", 1.1, 1.2);
        fatorRisco = new FatorRisco("Densidade", "Densidade de...");
        peso = Peso.criarPeso(3);
        contribuicao = Contribuicao.contribuicaoPositiva();
        necessidade = Necessidade.necessidadeObrigatoria();

        celulaBase = new CelulaBase(cobertura, envolvente, fatorRisco);
        celulaCaracterizada = new CelulaCaracterizada(peso, contribuicao, necessidade, cobertura, envolvente, fatorRisco);
        cobertura02 = new Cobertura("Incendio2", "Seguro para incendios");

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of exportCelula method, of class CelulaCaracterizada.
     */
    @Test
    public void testExportCelula() {
        System.out.println("exportCelula");
        CelulaCaracterizada instance = celulaCaracterizada;
        String[] expResult = celulaCaracterizada.toExporterDTO().get(0);
        String[] result = instance.toExporterDTO().get(0);
        assertArrayEquals(expResult, result);

    }

    /**
     * Test of match method, of class CelulaCaracterizada.
     */
    @Test
    public void testMatchBetweenCelulasCaracterizadas() {
        System.out.println("match");
        Celula c = celulaCaracterizada;
        CelulaCaracterizada instance = new CelulaCaracterizada(peso, contribuicao, necessidade, cobertura, envolvente, fatorRisco);
        boolean expResult = true;
        boolean result = instance.match(c);
        assertEquals(expResult, result);
    }

    /**
     * Test of no match method, of class CelulaCaracterizada.
     */
    @Test
    public void testNoMatchBetweenCelulasCaracterizada() {
        System.out.println("no match");
        Celula c = celulaCaracterizada;
        CelulaCaracterizada instance = new CelulaCaracterizada(peso, contribuicao, necessidade, cobertura02, envolvente, fatorRisco);
        boolean expResult = false;
        boolean result = instance.match(c);
        assertEquals(expResult, result);
    }

}
