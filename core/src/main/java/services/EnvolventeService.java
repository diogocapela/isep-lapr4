package services;

import models.Envolvente;
import repository.EnvolventeRepository;

import java.io.Serializable;

public class EnvolventeService implements Serializable {
    private final EnvolventeRepository er = new EnvolventeRepository();

    public EnvolventeService() {
        /**/
    }

    public Envolvente createEnvolvente(String titulo, String descricao, Double latitude, Double longitude) {
        Envolvente envolvente = er.findByDescription(descricao);

        if (envolvente == null) {
            //não existe, criar
            envolvente = new Envolvente(titulo, descricao, latitude, longitude);
            er.add(envolvente);
        }

        return envolvente;
    }

    public boolean existsOnDbByDescription(String description) {
        return er.findByDescription(description) != null;
    }

    public Envolvente getEnvolventeByDescription(String description) {
        return er.findByDescription(description);
    }

    public boolean existsOnDbById(Long id) {
        return er.findById(id) != null;
    }

    public Envolvente getEnvolventeById(Long id) {
        return er.findById(id);
    }
}
