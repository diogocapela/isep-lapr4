package controllers;

import services.CoberturaService;
import services.EmailService;
import settings.Settings;

public class AddCoberturaController {

    private final CoberturaService CS = new CoberturaService();

    public void addCobertura(String titulo, String descricao) {
        EmailService.sendEmail("Criação de uma nova cobertura", "\n\n" + titulo + " foi criado!\n", Settings.defaultemail);
        CS.createCobertura(titulo, descricao);
    }

}
