package rest.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "output")
public class SE01Output {

    @XmlElement
    private String message;

    protected SE01Output() {
    }

    public SE01Output(String message) {
        this.message = message;
    }
}
