package audit.lapr4;

import audit.Event;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-18T20:40:47")
@StaticMetamodel(Event.class)
public class Event_ { 

    public static volatile SingularAttribute<Event, String> mwhen;
    public static volatile SingularAttribute<Event, String> mmessage;
    public static volatile SingularAttribute<Event, String> mwho;
    public static volatile SingularAttribute<Event, String> maction;
    public static volatile SingularAttribute<Event, String> mnotes;
    public static volatile SingularAttribute<Event, String> mwhere;
    public static volatile SingularAttribute<Event, Long> id;

}