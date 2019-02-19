package raconsole.ui;

import models.avaliacao.Caso;
import models.avaliacao.ObjetoSeguro;
import raconsole.controller.AR04Controller;

import java.util.Scanner;

public class AR04UI5 {

    private static final AR04Controller ar4controller = new AR04Controller();
    private static Scanner scanner = new Scanner(System.in);

    public void atribuirDirectamenteResultados() {
        boolean isOver = false;
        while (!isOver) {
            System.out.println("\n====================================================================================");
            System.out.println("Atribuir Directamente Resultados");
            System.out.println("====================================================================================");
            System.out.println("INSIRA O ID DO CASO:");
            Long id = scanner.nextLong();
            Caso caso = ar4controller.getCaso(id);
            System.out.println("\n====================================================================================");
            System.out.println("Caso\n");
            System.out.println("ID " + caso.getID() + "\n");
            System.out.println("Objetos Seguros");
            System.out.println("====================================================================================\n");
            for (ObjetoSeguro obj : caso.objetosSegurados()) {
                System.out.println("Descricao " + obj.getDescricao());
                System.out.println("Latitude " + obj.getLatitude());
                System.out.println("Longitude " + obj.getLongitude() + "\n");
                System.out.println("      ====================================================================================");
                System.out.println("INSIRA UMA FUNDAMENTAÇÃO PARA ESTE OBJECTO SEGURO:");
                String fundamentacao = scanner.nextLine();
                obj.setFundamentacao(fundamentacao);
                System.out.println("\n====================================================================================");
            }
            System.out.println("QUER FAZER ALTERAÇÕES? (S/N) - se não fizer alterações grava na base de dados");
            String resp = scanner.nextLine();
            if (!resp.equalsIgnoreCase("s")) {
                isOver = true;
                ar4controller.saveCaso(caso);
                System.out.println("CASO GRAVADO COM SUCESSO!");
            }
        }
    }
}
