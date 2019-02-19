package ui;

import controllers.AddCoberturaController;

import java.util.Scanner;

public class AddCoberturaUI {

    private final Scanner scanner = new Scanner(System.in);
    private final AddCoberturaController controller = new AddCoberturaController();

    public void addCobertura() {
        System.out.println("Add Cobertura:");
        System.out.println("=============================");
        System.out.println("Titulo:");
        String titulo = scanner.nextLine();
        System.out.println("Descricao:");
        String descricao = scanner.nextLine();
        System.out.println("Adding coverage...");
        controller.addCobertura(titulo, descricao);
        System.out.println("Cobertura created  successfully!\n");
    }

}
