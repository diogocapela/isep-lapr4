package modelsDTO;

public class CelulaDetalhadaDTO extends CelulaCaracterizadaDTO {
    private final EscalaDTO escala;

    public CelulaDetalhadaDTO(CoberturaDTO cobertura, EnvolventeDTO envolvente, FatorRiscoDTO fatorRisco,
                              PesoDTO peso, ContribuicaoDTO contribuicao, NecessidadeDTO necessidade,
                              EscalaDTO escala) {
        super(cobertura, envolvente, fatorRisco, peso, contribuicao, necessidade);
        this.escala = escala;
    }

    public CoberturaDTO getCoberturaDTO() {
        return super.getCoberturaDTO();
    }

    public EnvolventeDTO getEnvolventeDTO() {
        return super.getEnvolventeDTO();
    }

    public FatorRiscoDTO getFatorRiscoDTO() {
        return super.getFatorRiscoDTO();
    }

    public PesoDTO getPeso() {
        return super.getPeso();
    }

    public ContribuicaoDTO getContribuicao() {
        return super.getContribuicao();
    }

    public NecessidadeDTO getNecessidade() {
        return super.getNecessidade();
    }

    public EscalaDTO getEscala() {
        return escala;
    }
}
