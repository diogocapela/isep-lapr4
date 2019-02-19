package rest.output;

import modelsDTO.CasoDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "output")
public class SE03OutputRoot {

    @XmlElement
    List<String> message = new ArrayList<>();

    @XmlElement
    Integer idCaso;

    @XmlElement
    String estado = "N/A";

    @XmlElement
    Integer avaliacao;

    public SE03OutputRoot() {

    }

    public SE03OutputRoot(String msg) {
        message.add(msg);
    }

    public SE03OutputRoot(List<String> message) {
        this.message.addAll(message);
    }

    public void SE03OutputAdd(String msg) {
        message.add(msg);
    }

    public void SE03OutputAddCasoInfo(CasoDTO cdto) {
        idCaso = cdto.getID().intValue();
    }

    public void SE03OutputAddEstado(String estado) {
        this.estado = estado;
    }

    public void SE03OutputAddAvaliacao(Integer av) {
        this.avaliacao = av;
    }
}
