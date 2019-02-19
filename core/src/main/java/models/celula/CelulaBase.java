package models.celula;

import adapters.Exporter;
import models.Cobertura;
import models.Envolvente;
import models.FatorRisco;
import modelsDTO.CelulaBaseDTO;
import settings.Settings;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CelulaBase extends Celula implements Serializable, Exporter {
    private static final Integer MATRIZ_BASE_NUMBER_OF_FIELDS = 3;
    // posição no ficheiro de input    

    // Variáveis de Instância
    //================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Cobertura cobertura;
    @OneToOne
    private Envolvente envolvente;
    @OneToOne
    private FatorRisco fatorRisco;
//    private Boolean consideracao;

    // Construtores
    //================================================================

    protected CelulaBase() {

    }

    public CelulaBase(Cobertura cobertura, Envolvente envolvente, FatorRisco fatorRisco) {
        this.cobertura = cobertura;
        this.envolvente = envolvente;
        this.fatorRisco = fatorRisco;
    }

    // Métodos
    //================================================================

    public boolean match(Celula c) {
        //CelulaBaseDTO outra = (CelulaBaseDTO) ((CelulaBase) c).toDTO();
        CelulaBaseDTO outra = ((CelulaBase) c).toBaseDTO();
        return this.cobertura.toDTO().equals(outra.getCoberturaDTO())
            && this.envolvente.toDTO().equals(outra.getEnvolventeDTO())
            && this.fatorRisco.toDTO().equals(outra.getFatorRiscoDTO());
    }

    @Override
    public String toString() {
        return "CelulaBase{" +
            "cobertura=" + cobertura +
            ", envolvente=" + envolvente +
            ", fatorRisco=" + fatorRisco +
            '}';
    }

    @Override
    public List<String[]> toExporterDTO() {
        List<String[]> result = new ArrayList<>();

        String[] celulaExport = new String[settings.Settings.CELULA_BASE_LENGTH];

        celulaExport[Settings.COBERTURA] = cobertura.toDTO().getTitulo();
        celulaExport[Settings.ENVOLVENTE] = envolvente.toDTO().getTitulo();
        celulaExport[Settings.FATOR_RISCO] = fatorRisco.toDTO().getTitulo();

        result.add(celulaExport);

        return result;
    }

    public CelulaBaseDTO toBaseDTO() {
        return new CelulaBaseDTO(this.cobertura.toDTO(), this.envolvente.toDTO(), this.fatorRisco.toDTO());
    }
}
