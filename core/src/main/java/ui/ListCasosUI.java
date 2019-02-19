package ui;

import controllers.ListCasosController;
import models.avaliacao.Caso;

import java.util.List;

public class ListCasosUI {
    private final ListCasosController controller = new ListCasosController();

    public void listCasos() {
        StringBuilder stringCasos = new StringBuilder();
        List<Caso> casos = controller.listCasos();

        stringCasos.append("\n==============================================================").
            append("\n            Listing Casos...").
            append("\n==============================================================");
        for (Caso c : casos) {
            stringCasos.append("\n    ").append(c.toString()).
                append("\n--------------------------------------------------------------");
        }
        stringCasos.append("\n            Casos listed successfully!").
            append("\n==============================================================");

        System.out.println(stringCasos);
    }
}
