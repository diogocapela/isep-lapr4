package models.matriz.eapli;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.celula.CelulaBase;
import models.matriz.EstadoMatriz;
import models.matriz.Matriz;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-18T20:40:47")
@StaticMetamodel(Matriz.class)
public class Matriz_ { 

    public static volatile SingularAttribute<Matriz, Date> data;
    public static volatile ListAttribute<Matriz, CelulaBase> celulas;
    public static volatile SingularAttribute<Matriz, EstadoMatriz> estadoMatriz;
    public static volatile SingularAttribute<Matriz, Long> id;
    public static volatile SingularAttribute<Matriz, Long> versao;

}