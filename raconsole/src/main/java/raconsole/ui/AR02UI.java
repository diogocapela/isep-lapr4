package raconsole.ui;

import raconsole.controller.AR02Controller;

import java.util.List;
import java.util.Scanner;

public class AR02UI {

    private final AR02Controller controller =
        new AR02Controller();
    private final Scanner scanner = new Scanner(System.in);

    public void consultarPedidosValidacao(String nomeAnalista) {

        System.out.println("\n===================================================================================");
        System.out.println(" AR02 - CASOS COM PEDIDO DE VALIDAÇÃO PENDENTE E NÂO ATRIBUÌDOS - User: " + nomeAnalista);
        System.out.println("===================================================================================");

        String distrito = pedeDistritoParaFiltragem();

        List<String> listRes = controller.getCasosEmValidacaoNaoAtribuidos(distrito);
        Long idCasoParaAtribuir;
        Boolean resAtribuicao = false;

        if (listRes.isEmpty()) {
            System.out.println("Não existem casos com pedidos de validação pendentes!");
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("Consulta terminada com sucesso!");
            System.out.println("===================================================================================");
            return;
        }

        for (int i = 0; i < listRes.size(); i++) {
            System.out.println((i + 1) + " - " + listRes.get(i) + "\n");
        }
        System.out.println("-----------------------------------------------------------------------------------");

        idCasoParaAtribuir = pedeIdCasoAAtribuir();

        if (idCasoParaAtribuir != null && !nomeAnalista.isEmpty()) {
            resAtribuicao = atribuirCasoParaValidacao(idCasoParaAtribuir, nomeAnalista);
        } else {
            System.out.println("Consulta terminada com sucesso!");
            System.out.println("===================================================================================");
            return;
        }

        if (resAtribuicao.equals(Boolean.FALSE)) {
            System.out.println("Não foi possível atribuir o caso com id " + idCasoParaAtribuir);
            System.out.println("===================================================================================");
            return;
        }

        System.out.println("Atribuição realizada com sucesso!");
        System.out.println("===================================================================================");
    }

    private String pedeDistritoParaFiltragem() {
        String distrito = "";

        System.out.println("Nome distrito (Press ENTER to skip this step): ");
        distrito = scanner.nextLine();
        System.out.println("-----------------------------------------------------------------------------------");

        return distrito;
    }

    private Long pedeIdCasoAAtribuir() {
        Long idCaso = null;
        String atribuir;
        Boolean confirma;

        System.out.println("Pretende atribuir validação? (s - Sim, n - Não)");
        atribuir = scanner.nextLine().trim();
        System.out.println("-----------------------------------------------------------------------------------");

        if (atribuir.equalsIgnoreCase("s")) {
            System.out.println("ID caso: ");
            idCaso = scanner.nextLong();
            System.out.println("-----------------------------------------------------------------------------------");
            if (idCaso != null) {
                confirma = confirmaDadosAtribuicao(idCaso);
                if (!confirma) {
                    idCaso = null;
                }
            }
        }

        return idCaso;
    }

    private Boolean confirmaDadosAtribuicao(Long idCasoParaAtribuir) {
        String res;

        scanner.nextLine();
        System.out.println("Confirma o id de caso para atribuição? " + idCasoParaAtribuir + "(s - Sim, n - Não)");
        res = scanner.nextLine().trim();
        System.out.println("-----------------------------------------------------------------------------------");

        return res.equalsIgnoreCase("s");

    }

    private Boolean atribuirCasoParaValidacao(Long idCaso, String nomeAnalista) {
        return controller.atribuirCaso(idCaso, nomeAnalista);
    }
}
