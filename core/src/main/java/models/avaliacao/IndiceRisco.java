package models.avaliacao;

import javax.persistence.Embeddable;

@Embeddable
public class IndiceRisco {

    // Variáveis de Instância
    //================================================================
    private Integer indiceRisco;

    // Construtores
    //================================================================
    protected IndiceRisco() {
    }

    public IndiceRisco(Integer indicieRisco) {
        this.indiceRisco = indicieRisco;
    }

    // Métodos
    //================================================================
    @Override
    public String toString() {
        return this.indiceRisco.toString();
    }


}
