package services;

import models.FatorRisco;
import repository.FatorRiscoRepository;

import java.io.Serializable;

public class FatorRiscoService implements Serializable {
    private final FatorRiscoRepository fs = new FatorRiscoRepository();

    public FatorRiscoService() {
        /**/
    }

    public FatorRisco createFatorRisco(String titulo, String descricao) {
        FatorRisco fr = fs.findByTitle(titulo);
        if (fr == null) {
            //não existe, criar
            fr = new FatorRisco(titulo, descricao);
            fs.add(fr);
        }
        return fr;
    }

    public boolean existsOnDbById(Long id) {
        return fs.findById(id) != null;
    }

    public boolean existsOnDbByTitle(String title) {
        return fs.findByTitle(title) != null;
    }

    public FatorRisco getFatorRiscoByTitle(String title) {
        return fs.findByTitle(title);
    }

    public FatorRisco getFatorRiscoById(Long id) {
        return fs.findById(id);
    }
}
