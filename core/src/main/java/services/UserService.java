package services;

import models.User;
import repository.UserRepository;

import java.io.Serializable;

public class UserService implements Serializable {
    private final UserRepository ur = new UserRepository();

    public UserService() {
        /**/
    }

    public User createUser(String nome, String email, String password) {
        User fr = ur.findByTitle(nome);
        if (fr == null) {
            //não existe, criar
            if (password != null || !password.isEmpty())
                fr = new User(email, nome, password);
            else
                fr = new User(email, nome, email);
            ur.add(fr);
        }
        return fr;
    }

    public boolean existsOnDbById(Long id) {
        return ur.findById(id) != null;
    }

    public boolean existsOnDbByTitle(String title) {
        return ur.findByTitle(title) != null;
    }

    public User getUserByTitle(String title) {
        return ur.findByTitle(title);
    }

    public User getUserById(Long id) {
        return ur.findById(id);
    }

    public Boolean authenticateUser(String nome, String secret) {
        if (!this.existsOnDbByTitle(nome)) {
            return false;
        }
        User u = this.getUserByTitle(nome);

        return AuthService.authenticate(u, secret);
    }

    public Boolean mockAuthenticateUser(String nome, String secret) {
        if (!this.existsOnDbByTitle(nome)) {
            return false;
        }
        return MockAuthService.authenticate(nome, secret);
    }
}
