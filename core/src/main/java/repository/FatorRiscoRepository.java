package repository;

import models.FatorRisco;
import settings.Settings;

import java.io.Serializable;
import java.util.List;

public class FatorRiscoRepository extends JPARepository<FatorRisco, Long> implements Serializable {

    @Override
    protected String persistenceUnitName() {
        return Settings.DB_PU;
    }

    public FatorRisco findByTitle(String title) {
        List<FatorRisco> lst = super.findAll();
        for (FatorRisco c : lst) {
            if (c.toDTO().getTitulo().equals(title)) {
                return c;
            }
        }
        return null;
    }
}
