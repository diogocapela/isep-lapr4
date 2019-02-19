package repository;

import models.avaliacao.ObjetoSeguro;
import settings.Settings;

import java.io.Serializable;

public class ObjetoSeguroRepository extends JPARepository<ObjetoSeguro, Long> implements Serializable {

    @Override
    protected String persistenceUnitName() {
        return Settings.DB_PU;
    }

}
