package raconsole.ui;

import georef.models.Location;
import raconsole.controller.AR06Controller;

import java.util.Scanner;

public class AR06UI {

    private final Scanner scanner = new Scanner(System.in);
    private final AR06Controller controller = new AR06Controller();

    public void registarEnvolvente() {
        boolean isDone = false;
        while (!isDone) {
            String name;
            String type;
            String address = null;
            Double latitude = null;
            Double longitude = null;
            String imageUrl;

            System.out.println("Registar Novo Envolvente\n");
            System.out.println("------------------------------------------------------------\n");

            System.out.println("Insira o nome do envolvente (e.g. Refinaria da Petrogal):");
            name = scanner.nextLine();

            System.out.println("Insira o tipo de envolvente (e.g. Local Inflamável):");
            type = scanner.nextLine();

            while (address == null || latitude == null || longitude == null) {
                System.out.println("Insira a localização do envolvente (e.g. Av. da Liberdade, Leça da Palmeira):");
                address = scanner.nextLine();
                System.out.println("\n");
                System.out.println("Obtendo coordenadas para \"" + address + "\"...");
                Location locationWithCoordinates = controller.getLocationFromPostalCode(address);
                if (locationWithCoordinates != null) {
                    latitude = locationWithCoordinates.getLatitude();
                    longitude = locationWithCoordinates.getLongitude();
                }
                if (latitude == null || longitude == null) {
                    System.out.println("Infelizmente não foi possível determinar a latitude e longitude para o endereço \"" + address + "\", tente novamente com um endereço válido! :(\n");
                } else {
                    System.out.println("Latitude: " + latitude);
                    System.out.println("Longitude: " + longitude);
                    System.out.println("\n");
                    System.out.println("Escolha uma estratégia para a recolha da imagem aérea:");
                    System.out.println("1. Google");
                    System.out.println("2. Bing");
                    System.out.println("Qualquer outra opção para usar a estratégia default.");
                    String strategy = scanner.nextLine();
                    System.out.println("\n");
                    if ("1".equalsIgnoreCase(strategy)) {
                        System.out.println("Gerando uma imagem aérea da Google para o endereço \"" + address + "\"...");
                        imageUrl = controller.downloadImageOfAddressGoogle(address);
                    } else if ("2".equalsIgnoreCase(strategy)) {
                        System.out.println("Gerando uma imagem aérea do Bing para o endereço \"" + address + "\"...");
                        imageUrl = controller.downloadImageOfAddressBing(address);
                    } else {
                        System.out.println("Gerando uma imagem aérea da estratégia default para o endereço \"" + address + "\"...");
                        imageUrl = controller.downloadImageOfAddress(address);
                    }
                    if (imageUrl == null) {
                        System.out.println("Infelizmente não foi possível gerar a imagem aéra para o endereço indicado! :(\n");
                    } else {
                        System.out.println("Imagem gerada com successo: " + imageUrl);
                    }
                }
            }
            System.out.println("\n");
            System.out.println("Pretende fazer alguma alteração? (Sim / Não)");
            String alteracao = scanner.nextLine();
            if (
                alteracao.equalsIgnoreCase("não") ||
                    alteracao.equalsIgnoreCase("nao") ||
                    alteracao.equalsIgnoreCase("n") ||
                    alteracao.equalsIgnoreCase("na") ||
                    alteracao.equalsIgnoreCase("no")
            ) {
                System.out.println("\n");
                System.out.println("A gravar o envolvente na base de dados...");
                controller.addEnvolvente(name, type, latitude, longitude);
                System.out.println("Novo envolvente gravado com sucesso! :)");
                isDone = true;
            }
        }
    }
}
