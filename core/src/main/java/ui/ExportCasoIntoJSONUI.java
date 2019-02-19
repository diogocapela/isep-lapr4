package ui;

import controllers.ExportCasoIntoJSONController;

import java.util.Scanner;

public class ExportCasoIntoJSONUI {

    private final Scanner scanner = new Scanner(System.in);
    private final ExportCasoIntoJSONController controller = new ExportCasoIntoJSONController();

    public void exportCasoIntoJSON() {
        System.out.println("Export Caso into a JSON File:");
        System.out.println("=============================");

        System.out.println("ID do Caso:");
        long ID = 0L;
        while (ID == 0L) {
            try {
                ID = Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("please introduce a number");
            }
        }

        System.out.println("Directório e nome do ficheiro JSON:");
        String filePath = scanner.nextLine();

        try {
            controller.exportCasoIntoJSON(ID, filePath);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro, ou o id especificado não existe ou o file path está incorrecto!");
        }


    }
}
