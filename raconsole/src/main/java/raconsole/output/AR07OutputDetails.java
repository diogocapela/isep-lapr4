package raconsole.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "envolvente")
public class AR07OutputDetails {

    @XmlElement
    private String distrito;
    @XmlElement
    private String tipo;
    @XmlElement
    private String descricao;
    @XmlElement
    private Double latitude;
    @XmlElement
    private Double longitude;

    protected AR07OutputDetails() {
    }

    public AR07OutputDetails(String distrito, String tipo, String descricao, Double latitude, Double longitude) {
        this.distrito = distrito == null ? "N/A" : distrito;
        this.tipo = tipo;
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return String.format(
            "LAT:%f\tLNG:%f\tDISTRITO:%s\tTIPO - DESC:%s - %s",
            this.latitude,
            this.longitude,
            this.distrito,
            this.tipo,
            this.descricao
        );
    }
}
