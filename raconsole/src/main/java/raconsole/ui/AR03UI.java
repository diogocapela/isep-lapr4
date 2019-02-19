package raconsole.ui;

import raconsole.Menu;
import raconsole.controller.AR03Controller;

import java.util.List;
import java.util.Scanner;

public class AR03UI {

    private final static Scanner scanner = new Scanner(System.in);
    private List<String> lista;
    private AR03Controller ar03Controller = new AR03Controller();
    private Long id;

    public void consultarAvaliacaoRisco(String nome) {
        System.out.println("\n=========================================================================");
        System.out.println(" Pedidos de Avaliação de Risco Pendentes de Avaliação do utilizador " + nome);
        System.out.println("\n=========================================================================");

        lista = ar03Controller.getPedidosdeAvaliacaodeRiscopendentes(nome);

        if (lista.isEmpty()) {
            System.out.println("Não existem pedidos de Avaliação de Risco Pendentes de Avaliação do utilizador " + nome);
            return;
        }

        int i;
        for (i = 0; i < lista.size(); i++) {
            System.out.println("ID: " + lista.get(i) + "\n");
        }
        System.out.println("\n=========================================================================");


        System.out.println("Pretende iniciar a validação de algum caso? (S/N)");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            System.out.println("\nDa listagem acima qual o ID da Lista");
            id = Long.valueOf(Integer.parseInt(scanner.nextLine()));
            AR04UI ar = new AR04UI();
            ar.analisarPedidoValidacaoByID(id);
            //ar.analisarPedidoValidacao(name, id);
        } else {
            Menu.loop(nome);
        }
    }
}

