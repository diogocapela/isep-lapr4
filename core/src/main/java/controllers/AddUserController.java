package controllers;

import models.User;
import repository.UserRepository;

public class AddUserController {

    private final UserRepository repository = new UserRepository();

    public User addUser(String email, String name) {
        //default behavior is the password is the same as email
        return repository.add(new User(email, name, email));
    }

}
