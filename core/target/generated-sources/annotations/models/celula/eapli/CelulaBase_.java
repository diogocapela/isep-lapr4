package models.celula.eapli;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Cobertura;
import models.Envolvente;
import models.FatorRisco;
import models.celula.CelulaBase;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-18T20:40:47")
@StaticMetamodel(CelulaBase.class)
public class CelulaBase_ extends Celula_ {

    public static volatile SingularAttribute<CelulaBase, Cobertura> cobertura;
    public static volatile SingularAttribute<CelulaBase, FatorRisco> fatorRisco;
    public static volatile SingularAttribute<CelulaBase, Envolvente> envolvente;

}