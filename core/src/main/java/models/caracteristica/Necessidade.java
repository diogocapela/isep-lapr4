package models.caracteristica;

import modelsDTO.NecessidadeDTO;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Necessidade implements Caracteristica, Serializable {
    private static final Integer FACULTATIVA = 0;
    private static final Integer OBRIGATORIA = 1;

    // Variáveis de Instância
    //================================================================

    private Integer necessidade;

    // Construtores
    //================================================================

    protected Necessidade() {
    }

    private Necessidade(Integer necessidade) {
        this.necessidade = necessidade;
    }

    public static Necessidade necessidadeFacultativa() {
        return new Necessidade(FACULTATIVA);
    }

    public static Necessidade necessidadeObrigatoria() {
        return new Necessidade(OBRIGATORIA);
    }

    // Métodos
    //================================================================


    public boolean isObrigatoria() {
        return Objects.equals(this.necessidade, OBRIGATORIA);
    }

    @Override
    public String toString() {
        return isObrigatoria() ? "Obrigatória" : "Facultativa";
    }

    public NecessidadeDTO toDTO() {
        return new NecessidadeDTO(this.toString());
    }


}
