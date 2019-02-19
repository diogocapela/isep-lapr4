package modelsDTO;

public class FatorRiscoDTO {
    private final String titulo;
    private final String descricao;

    public FatorRiscoDTO(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof FatorRiscoDTO))
            return false;

        FatorRiscoDTO other = (FatorRiscoDTO) obj;

        return other.titulo.equals(this.titulo) && other.descricao.equals(this.descricao);
    }
}
