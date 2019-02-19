package raconsole.ui;

import raconsole.controller.AR07Controller;

import java.util.List;
import java.util.Scanner;

public class AR07UI {

    private final Scanner scanner = new Scanner(System.in);
    private AR07Controller ar07Controller;
    private Double latitude1;
    private Double longitude1;
    private Double latitude2;
    private Double longitude2;
    private String distrito;

    public void listarEnvolventesRegistadas() {

        System.out.println("\n=========================================================================");
        System.out.println(" AR07 - LISTAR ENVOLVENTES REGISTADAS NO SISTEMA ");
        System.out.println("\n=========================================================================");

        pedeDistritoParaFiltragem();

        pedeDoisParesCoordenadasParaFiltragem();

        ar07Controller = new AR07Controller(latitude1, longitude1, latitude2, longitude2, distrito);

        boolean mostar = mostrarEnvolventes();
        if (mostar) {
            exportarResultados();
        }

        System.out.println("\n=========================================================================");
    }

    private void pedeDoisParesCoordenadasParaFiltragem() {
        System.out.println("Deseja fornecer coordenadas para filtrar? (s/n): ");
        String filtrar = scanner.nextLine();
        if (filtrar.equalsIgnoreCase("s")) {
            System.out.println("Coordenada Latitude 1:");
            latitude1 = pedeCoordenadaParaFiltragem();
            System.out.println("Coordenada Longitude 1:");
            longitude1 = pedeCoordenadaParaFiltragem();
            System.out.println("Coordenada Latitude 2:");
            latitude2 = pedeCoordenadaParaFiltragem();
            System.out.println("Coordenada Longitude 2:");
            longitude2 = pedeCoordenadaParaFiltragem();
        } else {
            latitude1 = null;
            longitude1 = null;
            latitude2 = null;
            longitude2 = null;
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    private Double pedeCoordenadaParaFiltragem() {
        Double result;
        while (true) {
            try {
                result = Double.parseDouble(scanner.nextLine());
                return result;
            } catch (NumberFormatException e) {
                System.out.println("Por favor forneça um número válido!");
            }
        }
    }

    private void pedeDistritoParaFiltragem() {
        System.out.println("Nome Distrito (Press ENTER to skip this step): ");
        String distrito = scanner.nextLine();
        System.out.println("-------------------------------------------------------------------------");

        if (distrito.equals("")) {
            this.distrito = null;
        } else {
            this.distrito = distrito;
        }
    }

    private boolean mostrarEnvolventes() {
        List<String> listaEnvolventes = ar07Controller.listarEnvolventesRegistadas();

        if (listaEnvolventes.size() > 0) {
            System.out.println("Envolventes Registadas no Sistema: ");
            for (String s : listaEnvolventes) {
                System.out.println(s);
            }
            System.out.println("-------------------------------------------------------------------------");

            System.out.println("Deseja exportar os resultados? (s/n): ");
            String exportar = scanner.nextLine();

            return exportar.equalsIgnoreCase("s");
        } else {
            System.out.println(String.format("Não existem Envolventes Registadas no Sistema %s: ", ar07Controller.filterToString()));
        }

        return false;
    }

    private void exportarResultados() {
        System.out.println("Qual o diretório de destino?");
        String directory = scanner.nextLine();
        String filePath = ar07Controller.exportarEnvolventesXHTML(directory);
        if (filePath == null) {
            System.out.println("Exportação falhou");
        } else {
            System.out.println("Exportação com sucesso - ficheiro encontra-se em:\n" + filePath);
        }
    }

}
