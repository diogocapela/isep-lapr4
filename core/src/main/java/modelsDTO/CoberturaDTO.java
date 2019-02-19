package modelsDTO;

public class CoberturaDTO {
    private final String titulo;
    private final String descricao;

    public CoberturaDTO(String titulo, String descricao) {
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
        if (!(obj instanceof CoberturaDTO))
            return false;

        CoberturaDTO other = (CoberturaDTO) obj;

        return other.titulo.equals(this.titulo) && other.descricao.equals(this.descricao);
    }
}
