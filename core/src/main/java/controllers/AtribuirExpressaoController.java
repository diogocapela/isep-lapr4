package controllers;

import models.FatorRisco;
import repository.FatorRiscoRepository;

public class AtribuirExpressaoController {

    FatorRiscoRepository repository = new FatorRiscoRepository();

    public Boolean atribuirExpressao(String titulo, String expressao) {
        FatorRisco fatorRisco = repository.findByTitle(titulo);
        if (fatorRisco == null) {
            return null;
        }

        Boolean result = fatorRisco.atribuirExpressao(expressao);
        if (result) {
            repository.update(fatorRisco);
        }
        repository.detach(fatorRisco);

        return result;
    }

}
