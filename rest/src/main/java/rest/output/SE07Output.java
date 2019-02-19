package rest.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "raconsole.output")
@XmlAccessorType(XmlAccessType.FIELD)
public class SE07Output {

    private SE07OutputData serverstart;
    private int aceptedcases;
    private int rejectedcases;
    private int activecases;
    private int averageresponsetime;
    private int maxCapacityPer60Seconds;

    protected SE07Output() {
    }

    public SE07Output(Date start, int acepted, int rejected, int active, int average, int rate) {
        this.serverstart = new SE07OutputData(start);
        this.aceptedcases = acepted;
        this.rejectedcases = rejected;
        this.activecases = active;
        this.averageresponsetime = average;
        this.maxCapacityPer60Seconds = rate;
    }
}


