package modelsDTO;

public class CelulaCaracterizadaDTO extends CelulaBaseDTO {
    private final PesoDTO peso;
    private final ContribuicaoDTO contribuicao;
    private final NecessidadeDTO necessidade;

    public CelulaCaracterizadaDTO(CoberturaDTO cobertura, EnvolventeDTO envolvente, FatorRiscoDTO fatorRisco,
                                  PesoDTO peso, ContribuicaoDTO contribuicao, NecessidadeDTO necessidade) {
        super(cobertura, envolvente, fatorRisco);
        this.peso = peso;
        this.contribuicao = contribuicao;
        this.necessidade = necessidade;
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
        return peso;
    }

    public ContribuicaoDTO getContribuicao() {
        return contribuicao;
    }

    public NecessidadeDTO getNecessidade() {
        return necessidade;
    }
}
