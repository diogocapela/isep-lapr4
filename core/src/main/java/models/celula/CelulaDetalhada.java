package models.celula;

import models.Cobertura;
import models.Envolvente;
import models.FatorRisco;
import models.caracteristica.Contribuicao;
import models.caracteristica.Necessidade;
import models.caracteristica.Peso;
import models.detalhe.Escala;
import modelsDTO.CelulaCaracterizadaDTO;
import modelsDTO.CelulaDetalhadaDTO;
import settings.Settings;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CelulaDetalhada extends CelulaCaracterizada implements Serializable {

    // Variáveis de Instância
    //================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long version;
    @Embedded
    private Escala escala;

    protected CelulaDetalhada() {

    }

    // Construtores
    //================================================================

    public CelulaDetalhada(Escala escala, Peso peso, Contribuicao contribuicao, Necessidade necessidade, Cobertura cobertura, Envolvente envolvente, FatorRisco fatorRisco) {
        super(peso, contribuicao, necessidade, cobertura, envolvente, fatorRisco);
        this.escala = escala;
    }


    // Métodos
    //================================================================

    @Override
    public String toString() {
        return super.toString() + "\nCelulaDetalhada{" +
            ", escala=" + escala +
            '}';
    }

    @Override
    public List<String[]> toExporterDTO() {
        List<String[]> result = new ArrayList<>();
        List<String[]> caracterizadaExporterDTO = super.toExporterDTO();

        String[] string = new String[settings.Settings.CELULA_DETALHADA_LENGTH];
        string[Settings.COBERTURA] = caracterizadaExporterDTO.get(0)[Settings.COBERTURA];
        string[Settings.ENVOLVENTE] = caracterizadaExporterDTO.get(0)[Settings.ENVOLVENTE];
        string[Settings.FATOR_RISCO] = caracterizadaExporterDTO.get(0)[Settings.FATOR_RISCO];
        string[Settings.PESO] = caracterizadaExporterDTO.get(0)[Settings.PESO];
        string[Settings.NECESSIDADE] = caracterizadaExporterDTO.get(0)[Settings.NECESSIDADE];
        string[Settings.CONTRIBUICAO] = caracterizadaExporterDTO.get(0)[Settings.CONTRIBUICAO];
        string[Settings.ESCALA_BAIXA_CELULA] = this.escala.toDTO().getEscalaBaixa().toString();
        string[Settings.ESCALA_MEDIA_CELULA] = this.escala.toDTO().getEscalaMedia().toString();
        string[Settings.ESCALA_ELEVADA_CELULA] = this.escala.toDTO().getEscalaElevada().toString();

        result.add(string);

        return result;
    }

    public CelulaDetalhadaDTO toDetalhadaDTO() {
        CelulaCaracterizadaDTO caracterizadaDTO = super.toCaracterizadaDTO();
        return new CelulaDetalhadaDTO(
            caracterizadaDTO.getCoberturaDTO(),
            caracterizadaDTO.getEnvolventeDTO(),
            caracterizadaDTO.getFatorRiscoDTO(),
            caracterizadaDTO.getPeso(),
            caracterizadaDTO.getContribuicao(),
            caracterizadaDTO.getNecessidade(),
            this.escala.toDTO()
        );
    }
}
