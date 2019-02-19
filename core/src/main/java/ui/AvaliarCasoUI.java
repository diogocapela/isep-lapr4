package ui;

import controllers.AvaliarCasoController;
import javafx.util.Pair;

import java.util.Scanner;

public class AvaliarCasoUI {

    private final Scanner scanner = new Scanner(System.in);
    private AvaliarCasoController controller = new AvaliarCasoController();

    public void avaliarCaso() {

        String result = "";
        Long idCaso;
        Pair<Long, Long> idMatrizes;
        boolean success = false;

        System.out.println("Introduza o ID do Caso: ");
        idCaso = scanner.nextLong();

        if (idCaso != 0L) {
            idMatrizes = pedeIdMatrizes();
            if (idMatrizes == null) {
                result = controller.avaliaCaso(idCaso);
            } else {
                result = controller.avaliaCasoCom2Matrizes(idCaso, idMatrizes);
            }
        } else {
            System.out.println("O sistema não conseguiu identificar o caso com ID: " + idCaso);
        }

        if (!result.isEmpty()) {
            System.out.println(result);
            System.out.println(String.format("Caso %d foi avaliado com sucesso", idCaso));
        } else {
            System.out.println(String.format("ERRO: Não foi possível avaliar o caso %d", idCaso));
        }
    }

    private Pair<Long, Long> pedeIdMatrizes() {
        Pair<Long, Long> idMatrizes = null;
        long idMatriz1;
        long idMatriz2;

        System.out.println("Introduza o ID da Matriz 1 (ou 0 para usar Matriz em Vigor):\n");
        idMatriz1 = scanner.nextLong();

        System.out.println("Introduza o ID da Matriz 2 (e 0 para usar matriz em Vigor):\n");
        idMatriz2 = scanner.nextLong();

        if (idMatriz1 != 0L && idMatriz2 != 0L) {
            idMatrizes = new Pair<>(idMatriz1, idMatriz2);
        } else {
            System.out.println("O caso será avaliado com a matriz em vigor.. ");
        }

        return idMatrizes;
    }

}
