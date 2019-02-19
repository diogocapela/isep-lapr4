package models.detalhe;

import adapters.Exporter;
import modelsDTO.EscalaDTO;
import settings.Settings;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Escala implements Serializable, Exporter {

    // Variáveis de Instância
    //================================================================

    private Integer escalaBaixa;
    private Integer escalaMedia;
    private Integer escalaElevada;

    // Construtores
    //================================================================

    protected Escala() {
    }

    private Escala(int valorBaixo, int valorMedio, int valorElevado) {
        this.escalaBaixa = valorBaixo;
        this.escalaMedia = valorMedio;
        this.escalaElevada = valorElevado;
    }

    public static Escala criarEscala(int valorBaixo, int valorMedio, int valorElevado) {
        return new Escala(valorBaixo, valorMedio, valorElevado);
    }

    // Métodos
    //================================================================

    @Override
    public String toString() {
        return escalaBaixa.toString() + " " +
            escalaMedia.toString() + " " +
            escalaElevada.toString();
    }

    @Override
    public List<String[]> toExporterDTO() {
        List<String[]> result = new ArrayList<>();
        String[] string = new String[Settings.ESCALA_LENGTH];

        string[Settings.ESCALA_BAIXA] = escalaBaixa.toString();
        string[Settings.ESCALA_MEDIA] = escalaMedia.toString();
        string[Settings.ESCALA_ELEVADA] = escalaElevada.toString();

        result.add(string);

        return result;
    }

    public EscalaDTO toDTO() {
        return new EscalaDTO(this.escalaBaixa, this.escalaMedia, this.escalaElevada);
    }
}
