package models.caracteristica;

import modelsDTO.ContribuicaoDTO;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Contribuicao implements Caracteristica, Serializable {
    private static final Integer POSITIVA = 1;
    private static final Integer NEGATIVA = -1;

    // Variáveis de Instância
    //================================================================

    private Integer contribuicao;

    // Construtores
    //================================================================

    protected Contribuicao() {
    }

    private Contribuicao(Integer contribuicao) {
        this.contribuicao = contribuicao;
    }

    public static Contribuicao contribuicaoPositiva() {
        return new Contribuicao(POSITIVA);
    }

    public static Contribuicao contribuicaoNegativa() {
        return new Contribuicao(NEGATIVA);
    }

    // Métodos
    //================================================================

    public boolean isPositiva() {
        return this.contribuicao.equals(POSITIVA);
    }

    @Override
    public String toString() {
        return isPositiva() ? "Positiva" : "Negativa";
    }

    public ContribuicaoDTO toDTO() {
        return new ContribuicaoDTO(this.toString());
    }


}
