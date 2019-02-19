package models.avaliacao;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class Demonstracao {

    // Variáveis de Instância
    //================================================================
    @Lob
    private String demonstracao;

    // Construtores
    //================================================================
    protected Demonstracao() {
    }

    public Demonstracao(String demonstracao) {
        this.demonstracao = demonstracao;
    }

    // Métodos
    //================================================================

    @Override
    public String toString() {
        return this.demonstracao;
    }


}
