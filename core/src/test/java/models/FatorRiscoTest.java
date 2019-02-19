/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class FatorRiscoTest {

    private FatorRisco fatorRisco;

    public FatorRiscoTest() {
    }

    @Before
    public void setUp() {
        fatorRisco = new FatorRisco("Densidade", "Densidade de...");
    }

    @BeforeEach
    public void setUpEach() {
        fatorRisco = new FatorRisco("Densidade", "Densidade de...");
    }

    /**
     * Titulo do fator de risco não pode ser nulo
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFatorRiscoTituloMustNotBeNull() {
        System.out.println("must have a title");
        FatorRisco instance = new FatorRisco(null, "Envolvente01");
    }

    /**
     * Test of getTitulo method, of class FatorRisco.
     */
    @Test
    public void testGetTitulo() {
        System.out.println("getTitulo");
        FatorRisco instance = fatorRisco;
        String expResult = "Densidade";
        String result = instance.toDTO().getTitulo();
        assertEquals(expResult, result);

    }

    @Test
    public void ensureExpressaoComMenosDe2ValoresDeEscalaNaoAtribuida() {
        boolean result = fatorRisco.atribuirExpressao("if (val < 5.00) then 3 else if (val < 10.00) then 1");
        assertFalse(result);
    }

    @Test
    public void ensureExpressaoComMaisDe2ValoresInvalidaDeEscalaNaoAtribuida() {
        boolean result = fatorRisco.atribuirExpressao("if (val < 5.00) then 3 else if (val < 10.00) then 2 else if (val >= 10.00) then a");
        assertFalse(result);
    }

    @Test
    public void ensureExpressaoNulaNaoAtribuida() {
        boolean result = fatorRisco.atribuirExpressao(null);
        assertFalse(result);
    }

    @Test
    public void ensureExpressaoValidaAtribuida() {
        boolean result = fatorRisco.atribuirExpressao("if (val < 5.00) then 3 else if (val < 10.00) then 2 else if (val >= 10.00) then 5");
        assertTrue(result);
    }

    @Test
    public void ensureEscalaHIGHSemExpressao() {
        Rating result = fatorRisco.determinarEscala(5.0);
        assertEquals(Rating.HIGH, result);
    }

    @Test
    public void ensureEscalaHIGHTestValNull() {
        Rating result = fatorRisco.determinarEscala(null);
        assertEquals(Rating.HIGH, result);
    }

    @Test
    public void ensureEscalaHIGHComExpressaoValida() {
        fatorRisco.atribuirExpressao("if (val < 1.50) then 1 else if (val < 2.50) then 2 else if (val >= 2.50) then 3");
        Rating result = fatorRisco.determinarEscala(3.0);
        assertEquals(Rating.HIGH, result);
    }

    @Test
    public void ensureEscalaMEDIUMComExpressaoValida() {
        fatorRisco.atribuirExpressao("if (val < 1.50) then 1 else if (val < 2.50) then 2 else if (val >= 2.50) then 3");
        Rating result = fatorRisco.determinarEscala(2.0);
        assertEquals(Rating.MEDIUM, result);
    }

    @Test
    public void ensureEscalaLOWComExpressaoValida() {
        fatorRisco.atribuirExpressao("if (val < 1.50) then 1 else if (val < 2.50) then 2 else if (val >= 2.50) then 4");
        Rating result = fatorRisco.determinarEscala(1.0);
        assertEquals(Rating.LOW, result);
    }
}
