/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.celula;

import models.Cobertura;
import models.Envolvente;
import models.FatorRisco;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CelulaBaseTest {
    private CelulaBase celulaBase;
    private Cobertura cobertura;
    private FatorRisco fatorRisco;
    private Envolvente envolvente;
    private Cobertura cobertura02;


    public CelulaBaseTest() {
    }


    @Before
    public void setUp() {
        cobertura = new Cobertura("Incendio", "Seguro para incêndios");
        envolvente = new Envolvente("Policia", "Envolvente em Policia.", 1.1, 1.2);
        fatorRisco = new FatorRisco("Densidade", "Densidade de...");


        cobertura02 = new Cobertura("Incendio2", "Seguro para incendios");
        celulaBase = new CelulaBase(cobertura, envolvente, fatorRisco);

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of match method, of class CelulaBase.
     */
    @Test
    public void testMatchBetweenCelulasBase() {
        System.out.println("match");
        Celula c = celulaBase;
        CelulaBase instance = new CelulaBase(cobertura, envolvente, fatorRisco);
        boolean expResult = true;
        boolean result = instance.match(c);
        assertEquals(expResult, result);

    }

    /**
     * Test of no match method, of class CelulaBase.
     */
    @Test
    public void testNoMatchBetweenCelulasBase() {
        System.out.println("no match");
        Celula c = celulaBase;
        CelulaBase instance = new CelulaBase(cobertura02, envolvente, fatorRisco);
        boolean expResult = false;
        boolean result = instance.match(c);
        assertEquals(expResult, result);

    }


    /**
     * Test of exportCelula method, of class CelulaBase.
     */
    @Test
    public void testExportCelula() {
        System.out.println("exportCelula");
        CelulaBase instance = celulaBase;
        String[] expResult = celulaBase.toExporterDTO().get(0);
        String[] result = instance.toExporterDTO().get(0);
        assertArrayEquals(expResult, result);

    }


}
