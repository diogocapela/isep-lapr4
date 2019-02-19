package repository;

import models.User;
import settings.Settings;

import java.io.Serializable;
import java.util.List;

public class UserRepository extends JPARepository<User, Long> implements Serializable {

    @Override
    protected String persistenceUnitName() {
        return Settings.DB_PU;
    }

    public User findByTitle(String nome) {
        List<User> lst = this.findAll();
        for (User c : lst) {
            if (c.toDTO().getName().equals(nome)) {
                return c;
            }
        }
        return null;
    }

}
