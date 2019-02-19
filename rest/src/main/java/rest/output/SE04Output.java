package rest.output;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "output")
public class SE04Output {

    @XmlElement(name = "titulo")
    private String titulo;

    @XmlElement(name = "local")
    private SE04OutputLocal local;

    @XmlElement(name = "avaliacaoA")
    private SE04OutputAvaliacaoA avaliacaoA;

    @XmlElement(name = "avaliacaoB")
    private SE04OutputAvaliacaoB avaliacaoB;

    @XmlElement(name = "sumario")
    private String sumario;

    protected SE04Output() {
    }

    public SE04Output(List<String> message) {
        this.titulo = "COMPARAÇÃO DE AVALIAÇÃO DE OBJECTO SEGURO COM MATRIZ A (ID = " +
            message.get(3) + ") E MATRIZ B (ID = " + message.get(4) + ")";
        this.local = new SE04OutputLocal(message);
        this.avaliacaoA = new SE04OutputAvaliacaoA(message);
        this.avaliacaoB = new SE04OutputAvaliacaoB(message);
        this.sumario = message.get(9);
    }
}
