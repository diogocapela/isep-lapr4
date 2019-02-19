/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.detalhe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Nuno
 */
public class EscalaTest {
    public Escala escala;

    public EscalaTest() {
    }


    @Before
    public void setUp() {
        int valorBaixo = 1;
        int valorMedio = 3;
        int valorElevado = 5;
        escala = Escala.criarEscala(valorBaixo, valorMedio, valorElevado);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of export method, of class Escala.
     */
    @Test
    public void testExport() {
        System.out.println("export");
        Escala instance = escala;
        String[] expResult = escala.toExporterDTO().get(0);
        String[] result = instance.toExporterDTO().get(0);
        assertArrayEquals(expResult, result);

    }


}
