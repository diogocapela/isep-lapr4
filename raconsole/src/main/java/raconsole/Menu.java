package raconsole;

import raconsole.ui.*;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Menu {

    private final static Scanner scanner = new Scanner(System.in);

    private final static AR02UI AR02UI = new AR02UI();
    private final static AR03UI AR03UI = new AR03UI();
    private final static AR04UI AR04UI = new AR04UI();
    private final static AR04UI5 AR04UI5 = new AR04UI5();
    private final static AR04UI6 AR04UI6 = new AR04UI6();
    private final static AR05UI AR05UI = new AR05UI();
    private final static AR06UI AR06UI = new AR06UI();
    private final static AR07UI AR07UI = new AR07UI();

    public static void loop(String username) {
        int option;
        do {
            option = 2000;
            do {
                try {
                    option = menu();
                } catch (Exception e) {
                    System.out.println("please insert a valid number");
                }
            }
            while (option == 2000);
            switch (option) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    AR02UI.consultarPedidosValidacao(username);
                    break;
                case 2:
                    AR03UI.consultarAvaliacaoRisco(username);
                    break;
                case 3:
                    AR04UI.analisarPedidoValidacao();
                    break;
                case 4:
                    AR04UI5.atribuirDirectamenteResultados();
                    break;
                case 5:
                    AR04UI6.solicitarReavaliacaoAutomatica();
                    break;
                case 6:
                    AR05UI.listarPedidosGlobal(username);
                    break;
                case 7:
                    AR06UI.registarEnvolvente();
                    break;
                case 8:
                    AR07UI.listarEnvolventesRegistadas();
                    break;
                default:
                    System.out.println("Please enter a valid option...");
                    break;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("sleep failed!");
            }
            System.out.println("Press ENTER to continue...");
            scanner.nextLine();
        } while (option != 0);
    }

    private static int menu() {
        StringBuilder stringMenu = new StringBuilder();
        stringMenu.
            append("\n=========================================================================").
            append("\n         I2S INSURANCE COMPANY - MENU ANALISTA DE RISCO").
            append("\n=========================================================================").
            append("\n   1. Consultar Pedidos De Validação Não Atribuídos (AR02)").
            append("\n   2. Meus Pedidos de Avaliação de Risco Pendentes de Avaliação (AR03)").
            append("\n   3. Analisar Pedido De Validação Atribuído a Mim (AR04)").
            append("\n   4. Atribuir Directamente Resultado(s) (AR04.5)").
            append("\n   5. Solicitar Reavaliação Automática (AR04.6)").
            append("\n   6. Consultar Pedidos por Mim Validados (AR05)").
            append("\n   7. Registar Envolvente (AR06)").
            append("\n   8. Obter Listagem de Envolventes Registadas no Sistema (AR07)").
            append("\n   0. Exit\n").
            append("\n=========================================================================").
            append("\n   Enter an option...\n");
        System.out.println(stringMenu);

        return new Scanner(System.in).nextInt();
    }

}
