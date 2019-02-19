package ui;

import controllers.ListCoberturasForaMatrizController;
import models.Cobertura;

import java.util.List;
import java.util.Scanner;

public class ListCoberturasForaMatrizUI {

    private final Scanner scanner = new Scanner(System.in);
    private final ListCoberturasForaMatrizController controller = new ListCoberturasForaMatrizController();

    public void listCoberturasForaMatriz() {
        System.out.println("Listar Coberturas não associadas a Matriz:");
        System.out.println("=============================");
        System.out.println("ID da Matriz:");
        Long ID = Long.parseLong(scanner.nextLine());

        List<Cobertura> list = controller.listCoberturasForaMatriz(ID);

        if (list == null) {
            System.out.println("Matriz não encontrada!\n");
        } else if (!list.isEmpty()) {
            for (Cobertura cb : list) {
                System.out.println(cb);
            }
        } else {
            System.out.println("Não há Coberturas configuradas na Matriz\n");
        }
    }
}
