/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author pushdword
 */
public class EmailServiceTest {

    public EmailServiceTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSendEmail() {
        EmailService.sendEmail("Test EAPLI unity", "this is just a test", "1140825@isep.ipp.pt");
    }

}
