package raconsole.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "casos")
public class AR05OutputDetails {

    @XmlElement(name = "caso")
    private List<AR05OutputDetails2> casos;


    protected AR05OutputDetails() {
    }

    public AR05OutputDetails(List<AR05OutputDetails2> casos) {
        this.casos = casos;
    }
}
