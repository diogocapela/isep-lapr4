package models.lapr4;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.FatorRisco;
import services.FatorRiscoService;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-18T20:40:47")
@StaticMetamodel(FatorRisco.class)
public class FatorRisco_ { 

    public static volatile SingularAttribute<FatorRisco, Integer> maxEscala;
    public static volatile SingularAttribute<FatorRisco, String> titulo;
    public static volatile SingularAttribute<FatorRisco, String> expressao;
    public static volatile SingularAttribute<FatorRisco, Long> id;
    public static volatile SingularAttribute<FatorRisco, FatorRiscoService> fs;
    public static volatile SingularAttribute<FatorRisco, Long> version;
    public static volatile SingularAttribute<FatorRisco, Integer> minEscala;
    public static volatile SingularAttribute<FatorRisco, String> descricao;

}