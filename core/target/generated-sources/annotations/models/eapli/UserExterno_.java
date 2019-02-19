package models.eapli;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Token;
import models.UserExterno;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-18T20:40:47")
@StaticMetamodel(UserExterno.class)
public class UserExterno_ { 

    public static volatile SingularAttribute<UserExterno, byte[]> salt;
    public static volatile SingularAttribute<UserExterno, byte[]> hashedPassword;
    public static volatile SingularAttribute<UserExterno, String> name;
    public static volatile SingularAttribute<UserExterno, Long> id;
    public static volatile SingularAttribute<UserExterno, Long> version;
    public static volatile SingularAttribute<UserExterno, String> email;
    public static volatile SingularAttribute<UserExterno, Token> token;

}