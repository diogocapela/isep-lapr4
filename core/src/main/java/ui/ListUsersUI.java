package ui;

import controllers.ListUsersController;
import models.User;

import java.util.List;

public class ListUsersUI {

    private final ListUsersController controller = new ListUsersController();

    public void listUsers() {
        System.out.println("Listing Users...");
        List<User> products = controller.listUsers();
        System.out.println("===============================================================");
        for (User product : products) {
            System.out.println(product);
        }
        System.out.println("\n");
        System.out.println("Users listed successfully!.\n");
    }

}
