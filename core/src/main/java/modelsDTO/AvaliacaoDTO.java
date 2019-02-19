package modelsDTO;

public class AvaliacaoDTO {
    private final Integer indiceRisco;
    private final String demonstracao;

    public AvaliacaoDTO(Integer indiceRisco, String demonstracao) {
        this.indiceRisco = indiceRisco;
        this.demonstracao = demonstracao;
    }

    public Integer getIndiceRisco() {
        return indiceRisco;
    }

    public String getDemonstracao() {
        return demonstracao;
    }
}
