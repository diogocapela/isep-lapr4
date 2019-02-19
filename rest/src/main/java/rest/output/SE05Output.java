package rest.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "output")
public class SE05Output {

    @XmlElement
    private List<String> result;

    protected SE05Output() {
    }

    public SE05Output(List<String> result) {
        this.result = result;
    }
}
