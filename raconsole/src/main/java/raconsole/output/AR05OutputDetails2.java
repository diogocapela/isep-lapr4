package raconsole.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "caso")
public class AR05OutputDetails2 {

    @XmlElement(name = "tempoValidacao")
    Long tempoValidacao;
    @XmlElement(name = "idCaso")
    private Long idCaso;
    @XmlElement(name = "indiceRisco")
    private Integer indiceRisco;
    @XmlElement(name = "dataValidacao")
    private String dataValidacao;

    protected AR05OutputDetails2() {
    }

    public AR05OutputDetails2(Long idCaso, Integer indiceRisco, String dataValidacao, Long tempoValidacao) {
        this.idCaso = idCaso;
        this.indiceRisco = indiceRisco;
        this.dataValidacao = dataValidacao;
        this.tempoValidacao = tempoValidacao;
    }
}
