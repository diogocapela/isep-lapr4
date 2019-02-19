package raconsole.ui;

import raconsole.controller.AR04Controller;

import java.util.Scanner;

public class AR04UI6 {

    private static final AR04Controller ar4controller = new AR04Controller();
    private static Scanner scanner = new Scanner(System.in);

    public void solicitarReavaliacaoAutomatica() {
        boolean isOver = false;
        while (!isOver) {
            System.out.println("\n========================");
            System.out.println("Solicitar Reavaliação Automatica");
            System.out.println("========================");
            System.out.println("INSIRA O ID DO CASO:");
            Long id = scanner.nextLong();
            ar4controller.solicitarReavaliacaoAutomatica(id);
            System.out.println("DONE!");
        }
    }

}
