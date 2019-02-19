package ui;

import controllers.ListMatrizesBaseController;
import models.matriz.Matriz;
import print.PrintMatriz;

import java.util.List;

public class ListMatrizesBaseUI {
    private final ListMatrizesBaseController controller = new ListMatrizesBaseController();

    public void listMatrizesBase() {
        System.out.println("Listing Matrizes Base...");
        List<Matriz> list = controller.listMatrizesBase();
        System.out.println("===============================================================");
        for (Matriz mb : list) {
            PrintMatriz pmb = new PrintMatriz();
            pmb.print(mb);
        }
        list.clear();
        System.out.println("Matrizes base listed successfully!.\n");
    }
}
