package rest.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "raconsole.output")
public class SE02Output {

    @XmlElement
    String message;

    protected SE02Output() {

    }

    public SE02Output(String message) {
        this.message = message;
    }
}
