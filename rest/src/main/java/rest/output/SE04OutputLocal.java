package rest.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "local")
public class SE04OutputLocal {

    @XmlElement(name = "descricaoObjeto")
    private String descricaoObjeto;

    @XmlElement(name = "latitude")
    private String latitude;

    @XmlElement(name = "longitude")
    private String longitude;

    protected SE04OutputLocal() {
    }

    public SE04OutputLocal(List<String> message) {
        this.descricaoObjeto = "Descrição Objecto Seguro (Local): " + message.get(0);
        this.latitude = message.get(1);
        this.longitude = message.get(2);
    }
}
