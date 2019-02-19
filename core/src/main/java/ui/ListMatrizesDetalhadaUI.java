package ui;

import controllers.ListMatrizesDetalhadaController;
import models.matriz.Matriz;
import print.PrintMatriz;

import java.util.List;

public class ListMatrizesDetalhadaUI {
    private final ListMatrizesDetalhadaController controller = new ListMatrizesDetalhadaController();

    public void listMatrizesDetalhadas() {
        System.out.println("Listing Matrizes Detalhadas...");
        List<Matriz> list = controller.listMatrizesDetalhadas();
        System.out.println("===============================================================");
        for (Matriz mb : list) {
            PrintMatriz pmb = new PrintMatriz();
            pmb.print(mb);
        }
        list.clear();
        System.out.println("Matrizes base listed successfully!.\n");
    }
}
