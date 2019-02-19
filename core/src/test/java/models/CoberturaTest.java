/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoberturaTest {

    private Cobertura cobertura;

    public CoberturaTest() {
    }


    @Before
    public void setUp() {
        cobertura = new Cobertura("Incendio", "Seguro para incêndios");
    }

    /**
     * Titulo da cobertura não pode ser nulo
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCoberturaTituloMustNotBeNull() {
        System.out.println("must have a title");
        Cobertura instance = new Cobertura(null, "Cobertura01");
    }


    /**
     * Test of getTitulo method, of class Cobertura.
     */
    @Test
    public void testGetTitulo() {
        System.out.println("getTitulo");
        Cobertura instance = cobertura;
        String expResult = "Incendio";
        String result = instance.toDTO().getTitulo();
        assertEquals(expResult, result);

    }

}
