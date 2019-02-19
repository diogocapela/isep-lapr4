package controllers;


import services.FatorRiscoService;

public class AddFatorRiscoController {

    private final FatorRiscoService frs = new FatorRiscoService();

    public void addFatorRisco(String titulo, String descricao) {
        frs.createFatorRisco(titulo, descricao);
    }
}
