package audit;

import models.User;
import services.EventService;
import services.UserService;

import java.time.Instant;

public class Auditlog {

    EventService es = new EventService();
    UserService us = new UserService();

    public String registerEvent(String who, String where, String action, String message, String notes) {
        try {
            User currentU = us.getUserByTitle(who);
            return es.registerAuditEventLog(currentU.toDTO().getName(), where, Instant.now().toString(), action, message, notes).toString();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

}
