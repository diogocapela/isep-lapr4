package ui;

import controllers.AvaliarCasoController;

public class AvaliarCasosBatchUI {
    private AvaliarCasoController controller = new AvaliarCasoController();

    public void avaliarCasosBatch() {
        boolean success = controller.avaliaCasosBatch();

        if (success) {
            System.out.println("Todos os casos por processar foram processados");
        } else {
            System.out.println("ERRO: Não foi possível avaliar todos os casos por processar");
        }
    }

}
