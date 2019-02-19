package raconsole.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "envolventes")
public class AR07Output {

    @XmlElement(name = "envolvente")
    private List<AR07OutputDetails> envolventes;

    protected AR07Output() {
    }

    public AR07Output(List<AR07OutputDetails> envolventes) {
        this.envolventes = envolventes;
    }
}
