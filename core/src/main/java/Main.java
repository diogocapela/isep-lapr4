import bootstrap.Bootstrap;
import repository.DatabaseManager;
import settings.Settings;

public class Main {

    public static void main(String[] args) {

        boolean deleteDb = false, populateDb = false;

        for (String s : args) {
            if ("-d".equals(s))
                deleteDb = true;
            if ("-p".equals(s))
                populateDb = true;
            if ("-mysql".equals(s))
                Settings.DB_PU = "DATABASE_02";
            if ("-local".equals(s))
                Settings.DB_PU = "DATABASE_01";
        }

        if (deleteDb) {
            DatabaseManager dm = new DatabaseManager();
            dm.deleteDatabase();
            dm.dropALLTables();
            System.out.println("DATA BASE FLUSHED OUT. PLEASE RE RUN THE PROGRAM WITH -p ARGUMENT TO POPULATE IF NEEDED");
            System.exit(0);
        }
        if (populateDb) {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.populateDatabase();
            System.out.println("DATA BASE POPULATED. PLEASE RE RUN THE PROGRAM WITHOUT ARGUMENTS");
            System.exit(0);
        }


        /*
        ⚠️ Just to test JSON Importer, do not touch this! ☢️

        JSONImporter jsonImporter = new JSONImporter();

        Caso caso;
        try {
            caso = jsonImporter.importCasoFromFile("./core/src/main/resources/json/caso-example.json");
            caso = jsonImporter.importCasoFromFile(".\src\main\resources\json\caso-example.json");
            System.out.println(caso);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        */

        Menu.loop();

    }

}
