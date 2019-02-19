package ui;

import controllers.ListEnvolventesController;
import modelsDTO.EnvolventeDTO;

import java.util.List;

public class ListEnvolventesUI {
    private final ListEnvolventesController controller = new ListEnvolventesController();

    public void listEnvolventes() {
        StringBuilder stringEnvolventes = new StringBuilder();
        List<EnvolventeDTO> envolventes = controller.listEnvolventes();

        stringEnvolventes.append("\n==============================================================").
            append("\n            Listing Envolventes...").
            append("\n==============================================================");
        for (EnvolventeDTO envDTO : envolventes) {
            stringEnvolventes.append("\n    ").append(envDTO.getTitulo()).append(" - ").append(envDTO.getDescricao()).
                append("\n--------------------------------------------------------------");
        }
        stringEnvolventes.append("\n            Envolventes listed successfully!").
            append("\n==============================================================");

        System.out.println(stringEnvolventes);
    }
}
