package models.avaliacao.lapr4;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.User;
import models.avaliacao.Caso;
import models.avaliacao.Validacao;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-18T20:40:47")
@StaticMetamodel(Validacao.class)
public class Validacao_ { 

    public static volatile SingularAttribute<Validacao, Caso> caso;
    public static volatile SingularAttribute<Validacao, Date> dataAtribuicao;
    public static volatile SingularAttribute<Validacao, Date> dataConclusao;
    public static volatile SingularAttribute<Validacao, Date> dataPedido;
    public static volatile SingularAttribute<Validacao, String> id;
    public static volatile SingularAttribute<Validacao, Long> version;
    public static volatile SingularAttribute<Validacao, User> analistaRisco;

}