package bootstrap;

import adapters.MatrizIO;
import controllers.*;
import models.Cobertura;
import models.Envolvente;
import models.FatorRisco;
import models.User;
import models.avaliacao.Caso;
import repository.*;
import settings.Settings;
import utils.CSVImporter;

import java.util.List;

public class Bootstrap {

    // Importers
    private final CSVImporter csvImporter = new CSVImporter();
    private final MatrizIO matrizIO = new MatrizIO();
    //    private final MatrizCaracterizadaIO matrizCaracterizadaIO = new MatrizCaracterizadaIO();
//    private final MatrizDetalhadaIO matrizDetalhadaIO = new MatrizDetalhadaIO();
    // Model Repositories
    private final CoberturaRepository coberturaRepository = new CoberturaRepository();
    private final EnvolventeRepository envolventeRepository = new EnvolventeRepository();
    private final FatorRiscoRepository fatorRiscoRepository = new FatorRiscoRepository();
    private final UserRepository userRepository = new UserRepository();
    // Matriz Repositories
    private final MatrizRepository matrizBaseRepository = new MatrizRepository();
    //private final MatrizCaracterizadaRepository matrizCaracterizadaRepository = new MatrizCaracterizadaRepository();
    //private final MatrizDetalhadaRepository matrizDetalhadaRepository = new MatrizDetalhadaRepository();
    // Caso Repository
    private final CasoRepository casoRepository = new CasoRepository();
    // Controllers
    private final ImportMatrizBaseController matrizBaseController = new ImportMatrizBaseController();
    private final CaracterizarMatrizController caracterizarMatrizController = new CaracterizarMatrizController();
    private final DetalharMatrizController detalharMatrizController = new DetalharMatrizController();
    private final PublicarMatrizController publicarMatrizController = new PublicarMatrizController();
    private final ImportCasoFromJSONController importCasoFromJSONController = new ImportCasoFromJSONController();

    public void populateDatabase() {
        System.out.println("Bootstrap: Populating database from CSV files...\n");

        List<Cobertura> coberturaList = csvImporter.importCobertura(Settings.PROJECT_PATH + "/main/resources/csv/coberturas.csv");
        List<Envolvente> envolventeList = csvImporter.importEnvolvente(Settings.PROJECT_PATH + "/main/resources/csv/envolventes.csv");
        List<FatorRisco> fatorRiscoList = csvImporter.importFatorRisco(Settings.PROJECT_PATH + "/main/resources/csv/factores-de-risco.csv");
        List<User> userList = csvImporter.importUser(Settings.PROJECT_PATH + "/main/resources/csv/users.csv");

        for (Cobertura cobertura : coberturaList) {
            coberturaRepository.add(cobertura);
        }

        for (Envolvente envolvente : envolventeList) {
            envolventeRepository.add(envolvente);
        }

        for (FatorRisco fatorRisco : fatorRiscoList) {
            fatorRiscoRepository.add(fatorRisco);
        }

        for (User user : userList) {
            userRepository.add(user);
        }

        // Adicionar Matriz 1 Base à Database -> Caracterizar  -> Detalhar
        //=============================================
        Long matrizBaseID = matrizBaseController.importMatrizBase("./core/src/main/resources/csv/b.csv");
        Boolean caracterizada = caracterizarMatrizController.caracterizarMatriz(matrizBaseID, "./core/src/main/resources/csv/c.csv");
        if (caracterizada) {
            detalharMatrizController.detalharMatriz(matrizBaseID, "./core/src/main/resources/csv/d.csv");
        }
        // Publicar Matriz
        Boolean publicada = publicarMatrizController.publicarMatriz(matrizBaseID);


        // Adicionar Matriz 2 Base à Database -> Caracterizar -> Detalhar
        // =============================================
        Long matrizBaseID2 = matrizBaseController.importMatrizBase("./core/src/main/resources/csv/b.csv");
        Boolean caracterizada2 = caracterizarMatrizController.caracterizarMatriz(matrizBaseID2, "./core/src/main/resources/csv/c.csv");
        if (caracterizada2) {
            detalharMatrizController.detalharMatriz(matrizBaseID2, "./core/src/main/resources/csv/d.csv");
        }

        // Adicionar Matriz 3 Base à Database -> Caracterizar -> Detalhar
        // =============================================
        Long matrizBaseID3 = matrizBaseController.importMatrizBase(".//core//src//main//resources//csv//b.csv");
        Boolean caracterizada3 = caracterizarMatrizController.caracterizarMatriz(matrizBaseID3, ".//core//src//main//resources//csv//c.csv");
        if (caracterizada3) {
            detalharMatrizController.detalharMatriz(matrizBaseID3, ".//core//src//main//resources//csv//d2.csv");
        }

        // Adicionar casos
        // Caso em Espera
        // =============================================
        try {
            Long idCasoEspera = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Long idCasoEspera2 = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Long idCasoEspera3 = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Long idCasoEspera4 = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Long idCasoEspera5 = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Long idCasoEspera6 = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Caso Processado
        //==============================================
        try {
            Long idCasoProcessado = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example2.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Long idCasoProcessado2 = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example2.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Long idCasoProcessado3 = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example2.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Long idCasoProcessado4 = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example3.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Casos em Validacao
        //======================================
        try {
            Long idCasoValidacao = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example.json");
            Caso casoValidacao = casoRepository.findById(idCasoValidacao);
            casoValidacao.emProcessamento();
            casoRepository.update(casoValidacao);
            casoValidacao.emValidacao();
            casoRepository.update(casoValidacao);
            casoValidacao.processado();
            casoRepository.update(casoValidacao);


        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Long idCasoValidacaoo2 = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example.json");
            Caso casoValidacao2 = casoRepository.findById(idCasoValidacaoo2);
            casoValidacao2.emProcessamento();
            casoRepository.update(casoValidacao2);
            casoValidacao2.emValidacao();
            casoRepository.update(casoValidacao2);
            casoValidacao2.processado();
            casoRepository.update(casoValidacao2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Long idCasoValidacaoo3 = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example.json");
            Caso casoValidacao3 = casoRepository.findById(idCasoValidacaoo3);
            casoValidacao3.emProcessamento();
            casoRepository.update(casoValidacao3);
            casoValidacao3.emValidacao();
            casoRepository.update(casoValidacao3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Long idCasoValidacaoo4 = importCasoFromJSONController.importCasoFromJSON("./core/src/main/resources/json/caso-example.json");
            Caso casoValidacao4 = casoRepository.findById(idCasoValidacaoo4);
            casoValidacao4.emProcessamento();
            casoRepository.update(casoValidacao4);
            casoValidacao4.emValidacao();
            casoRepository.update(casoValidacao4);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Matriz matrizBase = matrizIO.importBase(Settings.PROJECT_PATH + "/main/resources/csv/matriz-base.csv");
        //matrizBaseRepository.add(matrizBase);

        // Adicionar Matriz Caracterizada à Database
        //MatrizCaracterizada matrizCaracterizada = matrizCaracterizadaIO.importObject(Settings.PROJECT_PATH + "/main/resources/csv/matriz-caracterizada.csv");
        //matrizCaracterizadaRepository.add(matrizCaracterizada);

        // Adicionar Matriz Detalhada à Database
        //MatrizDetalhada matrizDetalhada = matrizDetalhadaIO.importObject(Settings.PROJECT_PATH + "/main/resources/csv/matriz-detalhada.csv");
        //matrizDetalhadaRepository.add(matrizDetalhada);

    }

}
