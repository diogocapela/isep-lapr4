package raconsole.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "objetosSeguros")
public class AR04OutputDetails {

    @XmlElement(name = "objetoSeguro")
    private List<AR04OutputDetails2> objetosSeguros;

    protected AR04OutputDetails() {
    }

    public AR04OutputDetails(List<AR04OutputDetails2> objetosSeguros) {
        this.objetosSeguros = objetosSeguros;
    }

}


