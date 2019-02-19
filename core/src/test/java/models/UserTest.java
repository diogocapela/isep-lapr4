package models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    public UserTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPassword() {

        User u = new User("mail@mail.com", "name", "mail@mail.com");
        Assert.assertTrue(u.validatePassword("mail@mail.com"));

    }
}
