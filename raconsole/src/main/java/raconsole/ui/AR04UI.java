package raconsole.ui;

import modelsDTO.CasoDTO;
import modelsDTO.CoberturaDTO;
import modelsDTO.ObjetoSeguroDTO;
import raconsole.controller.AR03Controller;
import raconsole.controller.AR04Controller;

import java.util.Scanner;

public class AR04UI {

    private static final AR04Controller ar4controller = new AR04Controller();
    private static final AR03Controller ar3controller = new AR03Controller();
    private static Scanner scanner = new Scanner(System.in);

    private static void exportarResultados(Long idCaso) {
        System.out.println("Qual o diretório de destino?");
        String directory = scanner.nextLine();
        String filePath = ar4controller.exportarCasoXHTML(directory, idCaso);
        if (filePath == null) {
            System.out.println("Exportação falhou");
        } else {
            System.out.println("Exportação com sucesso - ficheiro encontra-se em:\n" + filePath);
        }
    }

    public void analisarPedidoValidacao() {
        System.out.println("\n===================================================================================");
        System.out.println("Avaliar o pedido, insira o ID do caso");
        System.out.println("===================================================================================");
        Long id = scanner.nextLong();
        analisarPedidoValidacaoByID(id);

    }

    public void analisarPedidoValidacaoByID(Long ID) {
        CasoDTO casoDTO = ar4controller.getCasoDTOFromID(ID);
        System.out.println("\n===================================================================================");
        System.out.println("Caso\n");
        System.out.println("ID " + casoDTO.getID() + "\n");
        System.out.println("Objetos Seguros");
        System.out.println("===================================================================================\n");
        for (ObjetoSeguroDTO obj : casoDTO.getObjetosSegurados()) {
            System.out.println("Descricao " + obj.getDescricao());
            System.out.println("Latitude " + obj.getLatitude());
            System.out.println("Longitude " + obj.getLongitude() + "\n");
            System.out.println("Coberturas\n");
            System.out.println("      ===================================================================================");
            for (CoberturaDTO cob : obj.getCoberturas()) {
                System.out.println("      Titulo " + cob.getTitulo());
                System.out.println("      Descricao " + cob.getDescricao());
                System.out.println("      ===================================================================================\n");
            }
            System.out.println("FIM OBJETO SEGURO");
            System.out.println("\n===================================================================================");
        }
        System.out.println("Estado " + casoDTO.getEstado() + "\n");
        scanner.nextLine();
        System.out.println("DESEJA Exportar ESTE CASO? (S/N)");
        String respostaXHTML = scanner.nextLine();
        if (respostaXHTML.equalsIgnoreCase("s")) {
            exportarResultados(ID);
        }
        System.out.println("DESEJA VALIDAR ESTE CASO? (S/N)");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            if (ar4controller.validarCaso(ID)) {
                System.out.println("Caso validado com sucesso\n");
            } else {
                System.out.println("Caso nao validado com sucesso\n");
            }
        } else {
            System.out.println("caso nao validado\n");
        }
        System.out.println("DESEJA ADICIONAR UM COMENTARIO? (S/N)");
        if (resposta.equalsIgnoreCase("s")) {
            System.out.println("ESCREVA O COMENTÁRIO A ADICIONAR:");
            String comentario = scanner.nextLine();
            ar4controller.setComent(comentario);
        }
    }

}
