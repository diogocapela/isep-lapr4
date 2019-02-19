package raconsole.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "objetoSeguro")
public class AR04OutputDetails2 {

    @XmlElement
    private String descricao;
    @XmlElement
    private Double latitude;
    @XmlElement
    private Double longitude;

    protected AR04OutputDetails2() {
    }

    public AR04OutputDetails2(String descricao, Double latitude, Double longitude) {
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return String.format(
            "LAT:%f\tLNG:%f - DESC:%s ",
            this.latitude,
            this.longitude,
            this.descricao
        );
    }
}


