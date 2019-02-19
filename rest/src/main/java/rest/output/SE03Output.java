package rest.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "root")
public class SE03Output {
    @XmlElementWrapper(name = "outputs")
    @XmlElement(name = "output")
    public List<SE03OutputRoot> output = new ArrayList<>();

    public SE03Output() {

    }

    public void addOutput(SE03OutputRoot output) {
        this.output.add(output);
    }

}
