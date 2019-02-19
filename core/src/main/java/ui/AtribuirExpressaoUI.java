package ui;

import controllers.AtribuirExpressaoController;

import java.util.Scanner;

public class AtribuirExpressaoUI {

    private final Scanner scanner = new Scanner(System.in);
    private final AtribuirExpressaoController controller = new AtribuirExpressaoController();

    public void atribuirExpressao() {
        System.out.println("Atribuir Expressao a Fator de Risco:");
        System.out.println("=============================");
        System.out.println("Qual é o titulo do Fator de Risco?:");
        String titulo = scanner.nextLine();
        System.out.println("Qual é a expresao a atribuir?:");
        String expressao = scanner.nextLine();
        Boolean result = controller.atribuirExpressao(titulo, expressao);
        if (result == null) {
            System.out.println("Fator de Risco não encontrado");
        } else if (result) {
            System.out.println("Expressao atribuida com sucesso");
        } else {
            System.out.println("Não foi possível atribuir a expressão");
        }
    }

}
