package repository;

import models.Envolvente;
import settings.Settings;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class EnvolventeRepository extends JPARepository<Envolvente, Long> implements Serializable {

    @Override
    protected String persistenceUnitName() {
        return Settings.DB_PU;
    }

    public List<Envolvente> findAllByTitle(String title) {
        List<Envolvente> result = new LinkedList<>();
        List<Envolvente> lst = super.findAll();
        for (Envolvente c : lst) {
            if (c.toDTO().getTitulo().equals(title)) {
                result.add(c);
            }
        }
        return result;
    }

    public Envolvente findByDescription(String description) {
        List<Envolvente> lst = super.findAll();
        for (Envolvente c : lst) {
            if (c.toDTO().getDescricao().equals(description)) {
                return c;
            }
        }
        return null;
    }

}
