package ui;

import controllers.ListFatoresRiscoController;
import models.FatorRisco;

import java.util.List;

public class ListFatoresRiscoUI {
    private final ListFatoresRiscoController controller = new ListFatoresRiscoController();

    public void listFatoresRisco() {
        StringBuilder stringFatoresRisco = new StringBuilder();
        List<FatorRisco> fatoresRisco = controller.listFatoresRisco();

        stringFatoresRisco.append("\n==============================================================").
            append("\n            Listing Fatores de Risco...").
            append("\n==============================================================");
        for (FatorRisco fr : fatoresRisco) {
            stringFatoresRisco.append("\n    ").append(fr.toDTO().getTitulo()).
                append("\n--------------------------------------------------------------");
        }
        stringFatoresRisco.append("\n            Fatores de Risco listed successfully!").
            append("\n==============================================================");

        System.out.println(stringFatoresRisco);
    }
}
