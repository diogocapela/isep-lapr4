package services;

import models.User;

import java.io.Serializable;

public class AuthService implements Serializable {

    /**
     * @param u      : User testing authentication (target user)
     * @param secret : Secret, shall be the password/token
     * @return true if authentication is valid, false if not
     */
    public static boolean authenticate(User u, String secret) {
        if (u == null) return false;
        return u.validatePassword(secret);
    }

}
