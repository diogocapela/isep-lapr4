package raconsole.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class AR01ControllerTest {

    AR01Controller ar01c = new AR01Controller();

    public AR01ControllerTest() {
    }

    @Test
    public void testNoValidUser() {
        assertTrue(ar01c.authenticateUser("notexiting", "vitorsilva@mail.com") == -1);
    }

    @Test
    public void testValid() {
        assertTrue(ar01c.authenticateUser("Beneli", "vitorsilva@mail.com") == 0);
    }

    @Test
    public void testBadPass() {
        assertTrue(ar01c.authenticateUser("Beneli", "vitorsilva@mail.comWRONG") == 1);
    }

    @Test
    public void testNoValidUserMock() {
        assertTrue(ar01c.mockAuthenticateUser("notexiting", "vitorsilva@mail.com") == -1);
    }

    @Test
    public void testValidMock() {
        assertTrue(ar01c.mockAuthenticateUser("Beneli", "vitorsilva@mail.com") == 0);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


}
