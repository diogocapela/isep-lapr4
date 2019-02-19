package ui;

import controllers.AddEnvolventeController;

import java.util.Scanner;

public class AddEnvolventeUI {
    private final Scanner scanner = new Scanner(System.in);
    private final AddEnvolventeController controller = new AddEnvolventeController();

    public void addEnvolvente() {
        System.out.println("Add Envolvente:");
        System.out.println("=============================");
        System.out.println("Titulo do Envolvente:");
        String tituloEnvolvente = scanner.nextLine();
        System.out.println("Descricao:");
        String descricao = scanner.nextLine();
        System.out.println("Latitude:");
        Double latitude = 0.0;
        while (latitude == 0.0) {
            try {
                latitude = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("please introduce a number");
            }
        }
        System.out.println("Latitude:");
        Double longitude = 0.0;
        while (longitude == 0.0) {
            try {
                longitude = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("please introduce a number");
            }
        }
        System.out.println("Adding envolvente...");
        controller.addEnvolvente(tituloEnvolvente, descricao, latitude, longitude);
        System.out.println("Envolvente added successfully!\n");
    }
}
