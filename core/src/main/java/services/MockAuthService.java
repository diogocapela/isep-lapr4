package services;

import models.User;

import java.io.Serializable;

public class MockAuthService implements Serializable {

    /**
     * @param u      : User testing authentication (target user)
     * @param secret : Secret, shall be the password/token
     * @return true if authentication is valid, false if not
     */
    public static boolean authenticate(String u, String secret) {
        if (u == null) return false;
        return AuthService.authenticate(new User(secret, u, secret /*password mocked as email*/), secret);
    }

}
