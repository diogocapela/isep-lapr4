package modelsDTO;

public class EscalaDTO {
    private final Integer escalaBaixa;
    private final Integer escalaMedia;
    private final Integer escalaElevada;

    public EscalaDTO(Integer escalaBaixa, Integer escalaMedia, Integer escalaElevada) {
        this.escalaBaixa = escalaBaixa;
        this.escalaMedia = escalaMedia;
        this.escalaElevada = escalaElevada;
    }

    public Integer getEscalaBaixa() {
        return escalaBaixa;
    }

    public Integer getEscalaMedia() {
        return escalaMedia;
    }

    public Integer getEscalaElevada() {
        return escalaElevada;
    }
}
