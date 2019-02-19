package models.avaliacao;

import adapters.Exporter;
import modelsDTO.AvaliacaoDTO;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Table
public class Avaliacao implements Exporter {

    // Variáveis de Instância
    //================================================================
    @Embedded
    private IndiceRisco indiceRisco;
    @Embedded
    private Demonstracao demonstracao;

    // Construtores
    //================================================================
    protected Avaliacao() {
    }

    // Métodos
    //================================================================

    public Avaliacao(IndiceRisco indiceRisco, Demonstracao demonstracao) {
        this.indiceRisco = indiceRisco;
        this.demonstracao = demonstracao;
    }

    @Override
    public String toString() {
        return "Avaliacao{" + "indiceRisco=" + indiceRisco + ", demonstracao=" + demonstracao + '}';
    }

    public List<String[]> toExporterDTO() {
        List<String[]> result = new ArrayList<>();

        String[] string = new String[2];
        string[0] = indiceRisco.toString();
        string[1] = demonstracao.toString();
        result.add(string);

        return result;
    }

    public AvaliacaoDTO toDTO() {
        return new AvaliacaoDTO(Integer.parseInt(this.indiceRisco.toString()), this.demonstracao.toString());
    }
}
