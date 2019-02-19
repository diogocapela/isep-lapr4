package raconsole.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Sumario")
public class AR05OutputSumario {

    @XmlElement(name = "totalCasos")
    private Integer totalCasos;

    @XmlElement(name = "tempoMedioAnalise")
    private Long tempoMedioAnalise;


    protected AR05OutputSumario() {
    }

    public AR05OutputSumario(Integer totalCasos, Long tempoMedioAnalise) {
        this.totalCasos = totalCasos;
        this.tempoMedioAnalise = tempoMedioAnalise;
    }
}
