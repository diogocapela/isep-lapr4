/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnvolventeTest {

    private Envolvente envolvente;

    public EnvolventeTest() {
    }

    @Before
    public void setUp() {

        envolvente = new Envolvente("Policia", "Envolvente em Policia.", 1.1, 1.2);
    }

    @After
    public void tearDown() {
    }

    /**
     * Titulo do envolvente não pode ser nulo
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEnvolventeTituloMustNotBeNull() {
        System.out.println("must have a title");
        Envolvente instance = new Envolvente(null, "Envolvente01", 1.2, 2.2);
    }

    /**
     * Test of getTitulo method, of class Envolvente.
     */
    @Test
    public void testGetTitulo() {
        System.out.println("getTitulo");
        Envolvente instance = envolvente;
        String expResult = "Policia";
        String result = instance.toDTO().getTitulo();
        assertEquals(expResult, result);

    }

}
