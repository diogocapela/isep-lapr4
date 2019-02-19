package models.avaliacao.lapr4;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.avaliacao.Caso;
import models.avaliacao.EstadoCaso;
import models.avaliacao.ObjetoSeguro;
import models.avaliacao.Validacao;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-18T20:40:47")
@StaticMetamodel(Caso.class)
public class Caso_ { 

    public static volatile ListAttribute<Caso, ObjetoSeguro> objetosSegurados;
    public static volatile SingularAttribute<Caso, EstadoCaso> estado;
    public static volatile SingularAttribute<Caso, Validacao> validacao;
    public static volatile SingularAttribute<Caso, Long> id;
    public static volatile SingularAttribute<Caso, String> comentario;
    public static volatile SingularAttribute<Caso, Long> version;

}