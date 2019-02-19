package ui;

import controllers.ListCoberturasControllers;
import models.Cobertura;

import java.util.List;

public class ListCoberturasUI {
    private final ListCoberturasControllers controller = new ListCoberturasControllers();

    public void listCoberturas() {
        StringBuilder stringCoberturas = new StringBuilder();
        List<Cobertura> coberturas = controller.listCoberturas();

        stringCoberturas.append("\n==============================================================").
            append("\n            Listing Coberturas...").
            append("\n==============================================================");
        for (Cobertura c : coberturas) {
            stringCoberturas.append("\n    ").append(c.toDTO().getDescricao()).
                append("\n--------------------------------------------------------------");
        }
        stringCoberturas.append("\n            Coberturas listed successfully!").
            append("\n==============================================================");

        System.out.println(stringCoberturas);
    }
}
