package rest.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@XmlRootElement(name = "Data")
@XmlAccessorType(XmlAccessType.FIELD)
public class SE07OutputData {

    private int ano;
    private int mes;
    private int dia;
    private String time;

    protected SE07OutputData() {
    }

    public SE07OutputData(Date start) {
        LocalDate localDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.ano = localDate.getYear();
        this.mes = localDate.getMonthValue();
        this.dia = localDate.getDayOfMonth();
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        this.time = localDateFormat.format(start.getTime());
    }

}


