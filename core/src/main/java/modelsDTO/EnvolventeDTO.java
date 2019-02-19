package modelsDTO;

public class EnvolventeDTO {
    private final String titulo;
    private final String descricao;
    private final Double latitude;
    private final Double longitude;

    public EnvolventeDTO(String titulo, String descricao, Double latitude, Double longitude) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof EnvolventeDTO))
            return false;

        EnvolventeDTO other = (EnvolventeDTO) obj;

        return other.titulo.equals(this.titulo) && other.descricao.equals(this.descricao) && other.latitude.equals(this.latitude) && other.longitude.equals(this.longitude);
    }
}
