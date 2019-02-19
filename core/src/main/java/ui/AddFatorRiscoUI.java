package ui;

import controllers.AddFatorRiscoController;

import java.util.Scanner;

public class AddFatorRiscoUI {

    private final Scanner scanner = new Scanner(System.in);
    private final AddFatorRiscoController controller = new AddFatorRiscoController();

    public void addFatorRisco() {
        System.out.println("Add FatorRisco:");
        System.out.println("=============================");
        System.out.println("Titulo do Fator de Risco:");
        String titulo = scanner.nextLine();
        System.out.println("Descricao do Fator de Risco");
        String descricao = scanner.nextLine();
        System.out.println("Adding risk factor...");
        controller.addFatorRisco(titulo, descricao);
        System.out.println("Risk Factor added successfully!\n");
    }
}
