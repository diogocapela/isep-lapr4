package services;

import audit.Event;
import repository.EventRepository;

import java.io.Serializable;

public class EventService implements Serializable {

    private final EventRepository er = new EventRepository();

    public Event registerAuditEventLog(String who, String where, String when, String action, String message, String notes) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();

        Event evento = new Event(who, where + "\n" + stackTrace[2] + "\n" + stackTrace[3], when, action, message, notes);
        er.add(evento);
        return evento;
    }
}
