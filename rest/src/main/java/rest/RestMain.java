package rest;

public class RestMain {
    public static void main(String[] args) {

        for (String s : args) {
            if ("-mysql".equals(s))
                settings.Settings.DB_PU = "DATABASE_02";
            if ("-local".equals(s))
                settings.Settings.DB_PU = "DATABASE_01";

        }

        try {
            new HTTPServer(Settings.PORT, 300, 50).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
