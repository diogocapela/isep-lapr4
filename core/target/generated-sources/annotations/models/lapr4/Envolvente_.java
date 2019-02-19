package models.lapr4;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Envolvente;
import services.EnvolventeService;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-18T20:40:47")
@StaticMetamodel(Envolvente.class)
public class Envolvente_ { 

    public static volatile SingularAttribute<Envolvente, Double> latitude;
    public static volatile SingularAttribute<Envolvente, String> titulo;
    public static volatile SingularAttribute<Envolvente, Long> id;
    public static volatile SingularAttribute<Envolvente, Long> version;
    public static volatile SingularAttribute<Envolvente, EnvolventeService> es;
    public static volatile SingularAttribute<Envolvente, String> descricao;
    public static volatile SingularAttribute<Envolvente, Double> longitude;

}