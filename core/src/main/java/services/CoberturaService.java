package services;

import models.Cobertura;
import repository.CoberturaRepository;

import java.io.Serializable;

public class CoberturaService implements Serializable {

    private final CoberturaRepository cr = new CoberturaRepository();

    public CoberturaService() {
        /**/
    }

    public Cobertura createCobertura(String titulo, String descricao) {

        Cobertura cobertura = cr.findByTitle(titulo);

        if (cobertura == null) {
            //não existe, criar
            cobertura = new Cobertura(titulo, descricao);
            cr.add(cobertura);
        }

        return cobertura;
    }

    public boolean existsOnDbById(Long id) {
        return cr.findById(id) != null;
    }

    public boolean existsOnDbByTitle(String title) {
        return cr.findByTitle(title) != null;
    }

    public Cobertura getCoberturaByTitle(String title) {
        return cr.findByTitle(title);
    }

    public Cobertura getCoberturaById(Long id) {
        return cr.findById(id);
    }
}
