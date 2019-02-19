package raconsole.ui;

import modelsDTO.CasoDTO;
import raconsole.controller.AR05Controller;

import java.util.List;
import java.util.Scanner;

public class AR05UI {

    private static Scanner scanner = new Scanner(System.in);
    AR05Controller ar05Controller = new AR05Controller();

    public void listarPedidosGlobal(String nomeAnalista) {
        // listarPedidosValidados(nomeAnalista);
        listarPedidosPorMimValidadosDecrescente(nomeAnalista);
        scanner.nextLine();
        System.out.println("Deseja proceder à exportação da informação para XHTML? (S / N)");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            exportarResultados(nomeAnalista);
        }
    }
    /*public void listarPedidosValidados(String nomeAnalista) {
        System.out.println("\n===================================================================================");
        System.out.println(" AR5 - CASOS JÁ AVALIADOS - User: " + nomeAnalista);
        System.out.println("===================================================================================");

        List<String> listaCasosDTOAnalista;

        listaCasosDTOAnalista = ar05Controller.getCasosValidadosPorAnalista(nomeAnalista);
        for (String temp : listaCasosDTOAnalista) {
            System.out.printf(temp + "\n\n");
        }
    }*/

    public void listarPedidosPorMimValidadosDecrescente(String nomeAnalista) {
        System.out.println("\n===================================================================================");
        System.out.println(" Casos já validados por ordem decrescente de data de pedido do Analista: " + nomeAnalista);
        System.out.println("===================================================================================");

        List<CasoDTO> casosDTOAnalista = ar05Controller.ordenarPedidosMaisRecentes(nomeAnalista);

        for (CasoDTO tempCasoDto : casosDTOAnalista) {
            System.out.println("\n" + ar05Controller.formatDTO(tempCasoDto));
            System.out.println("Tempo decorrido entre atrubuição e validação de caso: " + ar05Controller.tempoOcorridoEntreDataConclusaoEDataAtribuido(tempCasoDto.getID()) + " segundos");

        }

        System.out.println("\n===================================================================================");
        System.out.println(ar05Controller.constroiSumarioPedidosApresentados(casosDTOAnalista));
        System.out.println("\n===================================================================================");
        System.out.println("Pretende filtrar pedidos para um período de tempo? (S / N)");
        String userInput = scanner.next();
        if (userInput.equalsIgnoreCase("s")) {
            System.out.println("Introduza o ano: ");
            int ano = scanner.nextInt();
            System.out.println("Introduza o mês inicial: ");
            int mesInicial = scanner.nextInt();
            System.out.println("Introduza o dia inicial: ");
            int diaInicial = scanner.nextInt();
            System.out.println("Introduza o mês final: ");
            int mesFinal = scanner.nextInt();
            System.out.println("Introduza o dia final: ");
            int diaFinal = scanner.nextInt();

            List<CasoDTO> casosDTOFiltrados = ar05Controller.filtrarCasosPeriodoTempo(ano, mesInicial, diaInicial, mesFinal, diaFinal, casosDTOAnalista);
            for (CasoDTO tempCasoDTO : casosDTOFiltrados) {
                System.out.println(ar05Controller.formatDTO(tempCasoDTO));
            }
        }
    }

    private void exportarResultados(String nomeAnalista) {
        System.out.println("Qual o diretório de destino?");
        String directory = scanner.nextLine();
        String filePath = ar05Controller.exportarResultadoConsultaXHTML(directory, nomeAnalista);
        if (filePath == null) {
            System.out.println("Exportação falhou");
        } else {
            System.out.println("Exportação com sucesso - ficheiro encontra-se em:\n" + filePath);
        }
    }

}
