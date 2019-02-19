package ui;

import controllers.AddUserController;

import java.util.Scanner;

public class AddUserUI {

    private final Scanner scanner = new Scanner(System.in);
    private final AddUserController controller = new AddUserController();

    public void addUser() {
        System.out.println("Register User:");
        System.out.println("=============================");
        System.out.println("EMAIL:");
        String email = scanner.nextLine();
        System.out.println("NAME:");
        String name = scanner.nextLine();
        System.out.println("Registering user...");
        controller.addUser(email, name);
        System.out.println("User registered successfully!\n");
    }

}
