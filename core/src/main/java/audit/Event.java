package audit;

import javax.persistence.*;

@Entity
@Table
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String mwho;
    @Column(nullable = false)
    private String mwhere;
    @Column(nullable = false)
    private String mwhen;
    @Column(nullable = false)
    private String maction;
    @Column(nullable = false)
    private String mmessage;
    @Column(nullable = true)
    private String mnotes;

    public Event() {
    }

    public Event(String who, String where, String when, String action, String message, String notes) {
        this.mwho = who;
        this.mwhere = where;
        this.mwhen = when;
        this.maction = action;
        this.mmessage = message;
        this.mnotes = notes;
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", who='" + mwho + '\'' +
            ", where='" + mwhere + '\'' +
            ", when='" + mwhen + '\'' +
            ", action='" + maction + '\'' +
            ", message='" + mmessage + '\'' +
            ", notes='" + mnotes + '\'' +
            '}';
    }
}
