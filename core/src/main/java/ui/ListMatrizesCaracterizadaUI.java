package ui;

import controllers.ListMatrizesCaracterizadaController;
import models.matriz.Matriz;
import print.PrintMatriz;

import java.util.List;

public class ListMatrizesCaracterizadaUI {
    private final ListMatrizesCaracterizadaController controller = new ListMatrizesCaracterizadaController();

    public void listMatrizesCaracterizadas() {
        System.out.println("Listing Matrizes Caracterizadas...");
        List<Matriz> list = controller.listMatrizesCaracterizadas();
        System.out.println("===============================================================");
        for (Matriz mb : list) {
            PrintMatriz pmb = new PrintMatriz();
            pmb.print(mb);
        }
        list.clear();
        System.out.println("Matrizes base listed successfully!.\n");
    }
}
