package settings;

public class Settings {

    // Change this to "./src" if you are using NetBeans!
    // Change this to "./core/src" if you are using JetBrains IntelliJ!
    public static final String PROJECT_PATH = "./core/src";

    // Celulas length values
    public static final Integer CELULA_BASE_LENGTH = 3;
    public static final Integer CELULA_CARATERIZADA_LENGTH = 6;
    public static final Integer CELULA_DETALHADA_LENGTH = 9;
    public static final Integer IMPORT_BASE_LENGTH = 3;
    public static final Integer IMPORT_CARATERIZADA_LENGTH = 6;
    public static final Integer IMPORT_DETALHADA_LENGTH = 6;

    // Celulas fields position
    public static final Integer COBERTURA = 0;
    public static final Integer ENVOLVENTE = 1;
    public static final Integer FATOR_RISCO = 2;
    public static final Integer PESO = 3;
    public static final Integer NECESSIDADE = 4;
    public static final Integer CONTRIBUICAO = 5;
    public static final Integer ESCALA_BAIXA_CELULA = 6;
    public static final Integer ESCALA_MEDIA_CELULA = 7;
    public static final Integer ESCALA_ELEVADA_CELULA = 8;

    // Escala length and fields position
    public static final Integer ESCALA_LENGTH = 3;
    public static final Integer ESCALA_BAIXA = 0;
    public static final Integer ESCALA_MEDIA = 1;
    public static final Integer ESCALA_ELEVADA = 2;

    // Celulas auxiliary values
    public static final String OBRIGATORIO = "Obrigatório";
    public static final String FACULTATIVO = "Facultativo";
    public static final String POSITIVA = "Positiva";
    public static final String NEGATIVA = "Negativa";

    // EstadoMatriz Matriz
    public static final String base = "base";
    public static final String caracterizada = "caracterizada";
    public static final String detalhada = "detalhada";
    public static final String publicada = "publicada";
    public static final String cancelada = "cancelada";

    public static final String defaultemail = "1050469@isep.ipp.pt";

    public static String DB_PU = "DATABASE_01";
}
