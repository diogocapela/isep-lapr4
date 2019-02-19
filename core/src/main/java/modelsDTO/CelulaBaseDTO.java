package modelsDTO;

public class CelulaBaseDTO {
    private final CoberturaDTO cobertura;
    private final EnvolventeDTO envolvente;
    private final FatorRiscoDTO fatorRisco;

    public CelulaBaseDTO(CoberturaDTO cobertura, EnvolventeDTO envolvente, FatorRiscoDTO fatorRisco) {
        this.cobertura = cobertura;
        this.envolvente = envolvente;
        this.fatorRisco = fatorRisco;
    }

    public CoberturaDTO getCoberturaDTO() {
        return cobertura;
    }

    public EnvolventeDTO getEnvolventeDTO() {
        return envolvente;
    }

    public FatorRiscoDTO getFatorRiscoDTO() {
        return fatorRisco;
    }
}
