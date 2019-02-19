package models.caracteristica;

import modelsDTO.PesoDTO;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Peso implements Caracteristica, Serializable {

    // Variáveis de Instância
    //================================================================

    private Integer peso;

    // Construtores
    //================================================================

    protected Peso() {
    }

    private Peso(Integer peso) {
        this.peso = peso;
    }

    public static Peso criarPeso(Integer valorPeso) {
        if (0 > valorPeso || valorPeso > 5) {
            throw new IllegalArgumentException(String.format("O valor %d é invalido para Peso", valorPeso));
        }
        return new Peso(valorPeso);
    }

    // Métodos
    //================================================================

    @Override
    public String toString() {
        return peso.toString();
    }

    public PesoDTO toDTO() {
        return new PesoDTO(this.peso);
    }
}
