package models.avaliacao.eapli;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Cobertura;
import models.avaliacao.Avaliacao;
import models.avaliacao.ObjetoSeguro;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-18T20:40:47")
@StaticMetamodel(ObjetoSeguro.class)
public class ObjetoSeguro_ { 

    public static volatile SingularAttribute<ObjetoSeguro, Double> latitude;
    public static volatile SingularAttribute<ObjetoSeguro, String> fundamentacao;
    public static volatile SingularAttribute<ObjetoSeguro, String> id;
    public static volatile SingularAttribute<ObjetoSeguro, Avaliacao> avaliacao;
    public static volatile SingularAttribute<ObjetoSeguro, Long> version;
    public static volatile ListAttribute<ObjetoSeguro, Cobertura> coberturas;
    public static volatile SingularAttribute<ObjetoSeguro, String> descricao;
    public static volatile SingularAttribute<ObjetoSeguro, Double> longitude;

}