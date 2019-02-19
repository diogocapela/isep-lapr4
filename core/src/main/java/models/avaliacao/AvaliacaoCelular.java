package models.avaliacao;

public class AvaliacaoCelular {

    private final Integer scoreObtido;
    private final Integer scoreMaximo;
    private final String demonstracaoObtida;
    private final String demonstracaoMaxima;

    private final String celula;

    public AvaliacaoCelular(Integer peso, Integer valorEscalaObtido, Integer valorEscalaMaxima, String cobertura, String fatorRisco) {
        scoreObtido = peso * valorEscalaObtido;
        scoreMaximo = peso * valorEscalaMaxima;
        demonstracaoObtida = String.format("(%d x %d)", peso, valorEscalaObtido);
        demonstracaoMaxima = String.format("(%d x %d)", peso, valorEscalaMaxima);
        celula = cobertura + " - " + fatorRisco;
    }

    public Integer getScoreObtido() {
        return scoreObtido;
    }

    public Integer getScoreMaximo() {
        return scoreMaximo;
    }

    public String getDemonstracaoObtida() {
        return demonstracaoObtida;
    }

    public String getDemonstracaoMaxima() {
        return demonstracaoMaxima;
    }

    public String getCelula() {
        return celula;
    }


}
