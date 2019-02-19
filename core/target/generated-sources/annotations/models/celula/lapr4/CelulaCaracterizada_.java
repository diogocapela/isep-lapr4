package models.celula.lapr4;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.caracteristica.Contribuicao;
import models.caracteristica.Necessidade;
import models.caracteristica.Peso;
import models.celula.CelulaCaracterizada;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-18T20:40:47")
@StaticMetamodel(CelulaCaracterizada.class)
public class CelulaCaracterizada_ extends CelulaBase_ {

    public static volatile SingularAttribute<CelulaCaracterizada, Necessidade> necessidade;
    public static volatile SingularAttribute<CelulaCaracterizada, Peso> peso;
    public static volatile SingularAttribute<CelulaCaracterizada, Contribuicao> contribuicao;

}