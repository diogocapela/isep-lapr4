package controllers;


import services.EnvolventeService;

public class AddEnvolventeController {

    private final EnvolventeService es = new EnvolventeService();

    public void addEnvolvente(String tituloEnvolvente, String descricao, Double latitude, Double longitude) {
        es.createEnvolvente(tituloEnvolvente, descricao, latitude, longitude);
    }
}
