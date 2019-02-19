package ui;

import controllers.ListFatoresForaMatrizController;
import models.FatorRisco;

import java.util.List;
import java.util.Scanner;

public class ListFatoresForaMatrizUI {

    private final Scanner scanner = new Scanner(System.in);
    private final ListFatoresForaMatrizController controller = new ListFatoresForaMatrizController();

    public void listFatoresForaMatriz() {
        System.out.println("Listar Fatores de Risco não associados a Matriz:");
        System.out.println("=============================");
        System.out.println("ID da Matriz:");
        Long ID = Long.parseLong(scanner.nextLine());

        List<FatorRisco> list = controller.listFatoresForaMatriz(ID);

        if (list == null) {
            System.out.println("Matriz não encontrada!\n");
        } else if (!list.isEmpty()) {
            for (FatorRisco fr : list) {
                System.out.println(fr);
            }
        } else {
            System.out.println("Não há Fatores de Risco configurados na Matriz\n");
        }
    }
}
