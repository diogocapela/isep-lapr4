package ui;

import controllers.ImportCasoFromJSONController;
import settings.Settings;

import java.util.Scanner;

public class ImportCasoFromJSONUI {

    private final Scanner scanner = new Scanner(System.in);
    private final ImportCasoFromJSONController controller = new ImportCasoFromJSONController();

    public void importCasoFromJSON() {
        System.out.println("Import Caso from a JSON File:");
        System.out.println("=============================");

        System.out.println("Nome do ficheiro JSON:");
        String fileName = scanner.nextLine();

        String filePath = Settings.PROJECT_PATH + "/main/resources/json/" + fileName + ".json";

        try {
            long ID = controller.importCasoFromJSON(filePath);
            System.out.println(String.format("Caso %d importado com sucesso!", ID));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("Ocorreu um erro, o file path está incorrecto!");
        }
    }
}
