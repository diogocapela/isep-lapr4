package ui;

import controllers.DetalharMatrizController;
import settings.Settings;

import java.util.Scanner;

public class DetalharMatrizUI {

    private final Scanner scanner = new Scanner(System.in);
    private final DetalharMatrizController controller = new DetalharMatrizController();

    public void detalharMatriz() {
        System.out.println("Importar Detalhes para Matriz Caracterizada:");
        System.out.println("=============================");
        System.out.println("ID da Matriz:");
        Long ID = 0L;
        while (ID == 0L) {
            try {
                ID = Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("please introduce a number");
            }
        }

        System.out.println("Nome do Ficheiro CSV:");
        String fileName = scanner.nextLine();

        String filePath = Settings.PROJECT_PATH + "/main/resources/csv/" + fileName + ".csv";

        boolean success = controller.detalharMatriz(ID, filePath);

        if (success) {
            System.out.println(String.format("Matriz %d detalhada com sucesso!\n", ID));
        } else {
            System.out.println(String.format("ERRO : Matriz %d não foi detalhada!", ID));
        }

    }

}
