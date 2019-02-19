import ui.*;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class Menu {
    private final static Scanner scanner = new Scanner(System.in);

    // List Entities
    private final static ListUsersUI listUsersUI = new ListUsersUI();
    private final static ListCoberturasUI listCoberturasUI = new ListCoberturasUI();
    private final static ListEnvolventesUI listEnvolventesUI = new ListEnvolventesUI();
    private final static ListFatoresRiscoUI listFatoresRiscoUI = new ListFatoresRiscoUI();
    private final static ListFatoresForaMatrizUI listFatoresForaMatrizUI = new ListFatoresForaMatrizUI();
    private final static ListCoberturasForaMatrizUI listCoberturasForaMatrizUI = new ListCoberturasForaMatrizUI();

    // List Matrizes
    private final static ListMatrizesBaseUI listMatrizesBaseUI = new ListMatrizesBaseUI();
    private final static ListMatrizesCaracterizadaUI listMatrizesCaracterizadasUI = new ListMatrizesCaracterizadaUI();
    private final static ListMatrizesDetalhadaUI listMatrizesDetalhadasUI = new ListMatrizesDetalhadaUI();
    private final static ExportMatrizBaseUI exportMatrizBaseUI = new ExportMatrizBaseUI();

    //List Casos
    private final static ListCasosUI listCasosUI = new ListCasosUI();

    // Add Entities
    private final static AddUserUI addUserUI = new AddUserUI();
    private final static AddCoberturaUI addCoberturaUI = new AddCoberturaUI();
    private final static AddEnvolventeUI addEnvolventeUI = new AddEnvolventeUI();
    private final static AddFatorRiscoUI addFatorRiscoUI = new AddFatorRiscoUI();

    // Processar Matrizes
    private final static ImportMatrizBaseUI importMatrizBaseUI = new ImportMatrizBaseUI();
    private final static CaracterizarMatrizUI caracterizarMatrizBaseUI = new CaracterizarMatrizUI();
    private final static DetalharMatrizUI detalharMatrizUI = new DetalharMatrizUI();
    private final static PublicarMatrizUI publicarMatrizUI = new PublicarMatrizUI();
    private final static RebasearMatrizUI rebasearMatrizUI = new RebasearMatrizUI();

    // JSON
    private final static ImportCasoFromJSONUI importCasoFromJSONUI = new ImportCasoFromJSONUI();
    private final static ProcessCasoFromJSONUI processCasoFromJSONUI = new ProcessCasoFromJSONUI();
    private final static ExportCasoIntoJSONUI exportCasoIntoJSONUI = new ExportCasoIntoJSONUI();

    // Avaliação de Casos
    private final static AvaliarCasoUI avaliadorCasoUI = new AvaliarCasoUI();
    private final static AvaliarCasosBatchUI avaliarCasosBatchUI = new AvaliarCasosBatchUI();

    // SG07
    private final static AtribuirExpressaoUI atribuirExpressaoUI = new AtribuirExpressaoUI();

    static void loop() {
        int option;
        do {
            option = 2000;
            do {
                try {
                    option = menu();
                } catch (NumberFormatException | InputMismatchException ex) {
                    System.out.println("please insert a valid number");
                }
            }
            while (option == 2000);
            switch (option) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    listUsersUI.listUsers();
                    break;
                case 2:
                    listCoberturasUI.listCoberturas();
                    break;
                case 3:
                    listEnvolventesUI.listEnvolventes();
                    break;
                case 4:
                    listFatoresRiscoUI.listFatoresRisco();
                    break;
                case 5:
                    listMatrizesBaseUI.listMatrizesBase();
                    break;
                case 6:
                    listMatrizesCaracterizadasUI.listMatrizesCaracterizadas();
                    break;
                case 7:
                    listMatrizesDetalhadasUI.listMatrizesDetalhadas();
                    break;
                case 8:
                    listCasosUI.listCasos();
                    break;
                case 20:
                    exportMatrizBaseUI.exportMatrizBase();
                    break;
                case 21:
                    importMatrizBaseUI.importMatrizBase();
                    break;
                case 22:
                    caracterizarMatrizBaseUI.caracterizarMatriz();
                    break;
                case 23:
                    detalharMatrizUI.detalharMatriz();
                    break;
                case 24:
                    publicarMatrizUI.publicarMatriz();
                    break;
                case 25:
                    publicarMatrizUI.publicarMatriz();
                    break;
                case 26:
                    rebasearMatrizUI.rebasearMatrizUI();
                    break;
                case 30:
                    processCasoFromJSONUI.processCasoFromJSON();
                    break;
                case 31:
                    importCasoFromJSONUI.importCasoFromJSON();
                    break;
                case 32:
                    exportCasoIntoJSONUI.exportCasoIntoJSON();
                    break;
                case 33:
                    avaliadorCasoUI.avaliarCaso();
                    break;
                case 35:
                    avaliarCasosBatchUI.avaliarCasosBatch();
                    break;
                case 36:
                    avaliadorCasoUI.avaliarCaso();
                    break;
                case 41:
                    listFatoresForaMatrizUI.listFatoresForaMatriz();
                    break;
                case 42:
                    listCoberturasForaMatrizUI.listCoberturasForaMatriz();
                    break;
                case 51:
                    addUserUI.addUser();
                    break;
                case 52:
                    addCoberturaUI.addCobertura();
                    break;
                case 53:
                    addEnvolventeUI.addEnvolvente();
                    break;
                case 54:
                    addFatorRiscoUI.addFatorRisco();
                    break;
                case 60:
                    atribuirExpressaoUI.atribuirExpressao();
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
            append("\n===============================================================").
            append("\n                     I2S INSURANCE COMPANY").
            append("\n===============================================================").
            append("\n   1. List Users ✅").
            append("\n   2. List Coberturas ✅").
            append("\n   3. List Envolventes ✅").
            append("\n   4. List Fatores de Risco ✅").
            append("\n   5. List Matrizes Base ✅").
            append("\n   6. List Matrizes Caracterizadas ✅").
            append("\n   7. List Matrizes Detalhadas ✅").
            append("\n   8. List Casos ✅\n").
            append("\n   20. Exportar matriz para um ficheiro CSV ✅").
            append("\n   21. Importar Matriz Base de um ficheiro CSV ✅").
            append("\n   22. Caracterizar Matriz Base CSV ✅").
            append("\n   23. Detalhar Matriz Caracterizada CSV ✅").
            append("\n   24. Publicar Matriz ✅").
            append("\n   25. Publicar Matriz em Data Futura ✅").
            append("\n   26. Reiniciar a Caracterização de uma Matriz ✅\n").
            append("\n   30. Importar Caso JSON + Avaliar Caso Matriz Publicada + Exportar Caso JSON ✅").
            append("\n   31. Importar Caso de um ficheiro JSON ✅").
            append("\n   32. Export Caso into JSON ✅").
            append("\n   33. Avaliar Caso ✅").
            append("\n   35. Processar Avaliações em Batch ✅").
            append("\n   36. Apresentar Comparação entre 2 Avaliações de Matrizes Diferentes ✅\n").
            append("\n   41. Listar Fatores de Risco não Configurados em Matriz ✅").
            append("\n   42. Listar Coberturas não Configuradas em Matriz ✅\n").
            append("\n   51. Add User ✅").
            append("\n   52. Add Cobertura ✅").
            append("\n   53. Add Envolvente ✅").
            append("\n   54. Add Fator de Risco ✅\n").
            append("\n   60. Atribuir Expressao a Fator de Risco\n").
            append("\n   0. Exit\n").
            append("\n===============================================================").
            append("\n   Enter an option...\n");
        System.out.println(stringMenu);

        return new Scanner(System.in).nextInt();
    }

}
