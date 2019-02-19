package rest.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "avaliacaoA")
public class SE04OutputAvaliacaoA {

    @XmlElement(name = "simulacaoA")
    private String simulacaoA;

    @XmlElementWrapper(name = "demonstracaoParte1")
    @XmlElement(name = "demonstracaoA")
    private List<String> demonstracaoA;

    @XmlElement(name = "indiceRiscoA")
    private String indiceRiscoA;

    protected SE04OutputAvaliacaoA() {
    }

    public SE04OutputAvaliacaoA(List<String> message) {
        this.simulacaoA = "Demonstração A (Score Obtido / Score Máximo) * 100";
        String temp[] = message.get(5).split("\n");
        this.demonstracaoA = new LinkedList<>();
        for (String s : temp) {
            demonstracaoA.add(s);
        }
        this.indiceRiscoA = message.get(6);
    }
}
