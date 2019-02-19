package models.celula;

import adapters.Exporter;
import models.Cobertura;
import models.Envolvente;
import models.FatorRisco;
import models.caracteristica.Contribuicao;
import models.caracteristica.Necessidade;
import models.caracteristica.Peso;
import modelsDTO.CelulaBaseDTO;
import modelsDTO.CelulaCaracterizadaDTO;
import settings.Settings;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CelulaCaracterizada extends CelulaBase implements Serializable, Exporter {

    // Variáveis de Instância
    //================================================================


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long version;
    @Embedded
    private Peso peso;
    @Embedded
    private Contribuicao contribuicao;
    @Embedded
    private Necessidade necessidade;

    // Construtores
    //================================================================

    protected CelulaCaracterizada() {
    }

    public CelulaCaracterizada(Peso peso, Contribuicao contribuicao, Necessidade necessidade, Cobertura cobertura, Envolvente envolvente, FatorRisco fatorRisco) {
        super(cobertura, envolvente, fatorRisco);
        this.peso = peso;
        this.contribuicao = contribuicao;
        this.necessidade = necessidade;
    }

    // Métodos
    //================================================================

    @Override
    public String toString() {
        return super.toString() + "\nCelulaCaracterizada{" +
            ", peso=" + peso +
            ", contribuicao=" + contribuicao +
            ", necessidade=" + necessidade +
            '}';
    }

    @Override
    public List<String[]> toExporterDTO() {
        List<String[]> result = new ArrayList<>();
        List<String[]> baseExporterDTO = super.toExporterDTO();

        String[] string = new String[settings.Settings.CELULA_CARATERIZADA_LENGTH];
        string[Settings.COBERTURA] = baseExporterDTO.get(0)[Settings.COBERTURA];
        string[Settings.ENVOLVENTE] = baseExporterDTO.get(0)[Settings.ENVOLVENTE];
        string[Settings.FATOR_RISCO] = baseExporterDTO.get(0)[Settings.FATOR_RISCO];
        string[Settings.PESO] = peso.toDTO().getPeso().toString();
        string[Settings.NECESSIDADE] = necessidade.toDTO().getNecessidade();
        string[Settings.CONTRIBUICAO] = contribuicao.toDTO().getContribuicao();

        result.add(string);

        return result;
    }

    public CelulaCaracterizadaDTO toCaracterizadaDTO() {
        CelulaBaseDTO baseDTO = super.toBaseDTO();
        return new CelulaCaracterizadaDTO(
            baseDTO.getCoberturaDTO(),
            baseDTO.getEnvolventeDTO(),
            baseDTO.getFatorRiscoDTO(),
            this.peso.toDTO(),
            this.contribuicao.toDTO(),
            this.necessidade.toDTO()
        );
    }
}
