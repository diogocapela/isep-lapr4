package rest.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "avaliacaoB")
public class SE04OutputAvaliacaoB {

    @XmlElement(name = "simulacaoB")
    private String simulacaoB;

    @XmlElementWrapper(name = "demonstracaoParte2")
    @XmlElement(name = "demonstracaoB")
    private List<String> demonstracaoB;

    @XmlElement(name = "indiceRiscoB")
    private String indiceRiscoB;

    protected SE04OutputAvaliacaoB() {
    }

    public SE04OutputAvaliacaoB(List<String> message) {
        this.simulacaoB = "Demonstração B (Score Obtido / Score Máximo) * 100";
        String temp[] = message.get(7).split("\n");
        this.demonstracaoB = new LinkedList<>();
        for (String s : temp) {
            demonstracaoB.add(s);
        }
        this.indiceRiscoB = message.get(8);
    }
}
