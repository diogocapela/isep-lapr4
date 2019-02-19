package ui;

import controllers.ExportMatrizBaseController;
import settings.Settings;

import java.util.Scanner;

public class ExportMatrizBaseUI {

    private final Scanner scanner = new Scanner(System.in);
    private final ExportMatrizBaseController controller = new ExportMatrizBaseController();

    public void exportMatrizBase() {
        System.out.println("Export Matriz Base:");
        System.out.println("=============================");

        System.out.println("Nome do Ficheiro CSV:");
        String fileName = scanner.next();

        System.out.println("ID matriz:");
        Long mID = scanner.nextLong();

        String filePath1 = Settings.PROJECT_PATH + "/main/resources/csv/" + fileName + ".csv";

        Long id = controller.exportMatrizBase(filePath1, mID);
        if (id == 0L) {
            System.out.println(String.format("Matriz Base exported and created successfully! - MATRIX ID == %d\n", id));
        } else {
            System.out.println("ERRO : Matriz Base não foi exportada");
        }
    }

}
