package ui;

import controllers.ImportMatrizBaseController;
import settings.Settings;

import java.util.Scanner;

public class ImportMatrizBaseUI {

    private final Scanner scanner = new Scanner(System.in);
    private final ImportMatrizBaseController controller = new ImportMatrizBaseController();

    public void importMatrizBase() {
        System.out.println("Import Matriz Base:");
        System.out.println("=============================");
        System.out.println("Nome do Ficheiro CSV:");
        String fileName = scanner.nextLine();

        String filePath1 = Settings.PROJECT_PATH + "/main/resources/csv/" + fileName + ".csv";
        String filePath2 = Settings.PROJECT_PATH + "/main/resources/csv/" + fileName + ".csv";

        Long id = controller.importMatrizBase(filePath1);

        if (id == 0L) {
            id = controller.importMatrizBase(filePath2);
        }

        if (id != 0L) {
            System.out.println(String.format("Matriz Base imported and created successfully! - MATRIX ID == %d\n", id));
        } else {
            System.out.println("ERRO : Matriz Base não foi importada");
        }
    }

}
