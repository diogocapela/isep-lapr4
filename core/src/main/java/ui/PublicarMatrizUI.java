package ui;

import controllers.PublicarMatrizController;

import java.util.Date;
import java.util.Scanner;

public class PublicarMatrizUI {

    private final Scanner scanner = new Scanner(System.in);
    private final PublicarMatrizController controller = new PublicarMatrizController();

    public void publicarMatriz() {
        System.out.println("Publicar Matriz Detalhada:");
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

        boolean success = controller.publicarMatriz(ID);

        if (success) {
            System.out.println(String.format("Matriz %d publicada com sucesso!\n", ID));
        } else {
            System.out.println(String.format("Matriz %d não foi publicada!\n", ID));
        }
    }

    public void publicarMatrizDataFutura() {
        System.out.println("Publicar Matriz Detalhada:");
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
        System.out.println(String.format("Data Futura aaaa/mm/dd (data atual = %s)", (new Date()).toString()));
        String dataString = "";
        boolean loopFlag = true;
        while (true) {
            if (!dataString.matches("[0-9]{4,}/[0-9]{1,2}/[0-9]{1,2}")) {
                dataString = scanner.next();
            } else {
                break;
            }
        }


        boolean success = controller.publicarMatrizDataFutura(ID, dataString);

        if (success) {
            System.out.println(String.format("Matriz %d publicada com sucesso!\n", ID));
        } else {
            System.out.println(String.format("Matriz %d não foi publicada!\n", ID));
        }
    }
}
