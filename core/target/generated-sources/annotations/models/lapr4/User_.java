package models.lapr4;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-18T20:40:47")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, byte[]> salt;
    public static volatile SingularAttribute<User, byte[]> hashedPassword;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, Long> version;
    public static volatile SingularAttribute<User, String> email;

}