/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Vitor Silva
 */
public class AuthServiceTest {

    public AuthServiceTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAuth() {
        String user = "Beneli";
        String pw = "vitorsilva@mail.com";

        Assert.assertTrue(AuthService.authenticate(new User(pw, user, pw), pw));

    }

    @Test
    public void testAuthWrong() {
        String user = "Beneli";
        String pw = "WRONGvitorsilva@mail.com";

        Assert.assertFalse(AuthService.authenticate(new User(pw + "cenas", user, pw + "cenas"), pw));

    }


}
