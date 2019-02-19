package rest.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "raconsole.output")
public class SE06Output {
    @XmlElement
    String message;

    protected SE06Output() {
    }

    public SE06Output(String message) {
        this.message = message;
    }
}


