package repository;

import audit.Event;
import settings.Settings;

import java.io.Serializable;

public class EventRepository extends JPARepository<Event, Long> implements Serializable {

    @Override
    protected String persistenceUnitName() {
        return Settings.DB_PU;
    }

}
