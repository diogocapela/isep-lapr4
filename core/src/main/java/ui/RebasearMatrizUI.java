package ui;


import controllers.RebasearMatrizController;

import java.util.Scanner;

public class RebasearMatrizUI {
    private final Scanner scanner = new Scanner(System.in);
    private final RebasearMatrizController controller = new RebasearMatrizController();

    public void rebasearMatrizUI() {
        System.out.println("Rebasear Matriz acima de Base:");
        System.out.println("=============================");

        System.out.println("ID da Matriz:");
        long ID = 0L;
        while (ID == 0L) {
            try {
                ID = Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("please introduce a number");
            }
        }
        Long newID = controller.rebasearMatriz(ID);

        if (newID != null) {
            System.out.println(String.format("Matriz %d rebaseada com sucesso -> ID de nova matriz %d \n", ID, newID));
        } else {
            System.out.println(String.format("Matriz %d não foi rebaseada!\n", ID));
        }
    }
}
