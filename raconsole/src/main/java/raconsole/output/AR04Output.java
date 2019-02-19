package raconsole.output;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "caso")
public class AR04Output {

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "objetosSeguros")
    private AR04OutputDetails objetosSeguros;

    @XmlElement(name = "estado")
    private String estado;


    protected AR04Output() {
    }

    public AR04Output(Long id, AR04OutputDetails objetosSeguros, String estado) {
        this.id = id;
        this.objetosSeguros = objetosSeguros;
        this.estado = estado;
    }
}
