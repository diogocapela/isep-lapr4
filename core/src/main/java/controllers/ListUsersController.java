package controllers;

import models.User;
import repository.UserRepository;

import java.util.LinkedList;
import java.util.List;

public class ListUsersController {

    private final UserRepository repository = new UserRepository();

    public List<User> listUsers() {
        List<User> result = new LinkedList<>();

        for (User u : repository.findAll()) {
            repository.detach(u);
            result.add(u);
        }

        return result;
    }

}
