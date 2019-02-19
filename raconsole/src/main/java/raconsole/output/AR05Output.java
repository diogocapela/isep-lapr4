package raconsole.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CasosValidados")
public class AR05Output {

    @XmlElement(name = "user")
    private String nomeAnalista;

    @XmlElement(name = "casos")
    private AR05OutputDetails casos;

    @XmlElement(name = "sumario")
    private AR05OutputSumario sumario;


    protected AR05Output() {
    }

    public AR05Output(String nomeAnalista, AR05OutputDetails casos, AR05OutputSumario sumario) {
        this.nomeAnalista = nomeAnalista;
        this.casos = casos;
        this.sumario = sumario;
    }
}
