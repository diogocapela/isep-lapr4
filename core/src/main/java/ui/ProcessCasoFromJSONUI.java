package ui;

import controllers.ProcessCasoFromJSONController;
import settings.Settings;

import java.util.Scanner;

public class ProcessCasoFromJSONUI {

    private final Scanner scanner = new Scanner(System.in);
    private final ProcessCasoFromJSONController controller = new ProcessCasoFromJSONController();

    public void processCasoFromJSON() {
        System.out.println("Processar Caso from a JSON:");
        System.out.println("=============================");

        System.out.println("Nome do ficheiro JSON INPUT:");
        String fileNameInput = scanner.nextLine();

        System.out.println("Nome do ficheiro JSON OUTPUT:");
        String fileNameOutput = scanner.nextLine();

        String filePathInput = Settings.PROJECT_PATH + "/main/resources/json/" + fileNameInput + ".json";
        String filePathOutput = Settings.PROJECT_PATH + "/main/resources/json/" + fileNameOutput + ".json";

        Long ID = controller.processCasoFromJSON(filePathInput, filePathOutput);
        if (ID != null) {
            System.out.println(String.format("Caso %d importado, processado e exportado com sucesso!", ID));
        } else {
            System.err.println(String.format("Erro no processamento do Caso %d!", ID));
        }
    }
}
