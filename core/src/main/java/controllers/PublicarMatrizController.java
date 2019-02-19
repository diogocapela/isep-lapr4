package controllers;

import models.matriz.Matriz;
import repository.MatrizRepository;

import java.util.Date;
import java.util.List;

public class PublicarMatrizController {

    private final MatrizRepository repository = new MatrizRepository();

    public boolean publicarMatriz(Long ID) {
        List<Matriz> matrizList = repository.findAll();
        Matriz result = repository.findById(ID);

        if (result != null) {
            for (Matriz m : matrizList) {
                if (m.isPublicada() && !m.equals(result)) {
                    m.cancelarMatriz();
                    repository.update(m);
                    break;
                }
            }

            result.publicarMatriz();

            if (result.isPublicada()) {
                return repository.update(result) != null;
            }
        }

        return false;
    }

    public boolean publicarMatrizDataFutura(Long ID, String dataFutura) {
        List<Matriz> matrizList = repository.findAll();
        Matriz result = repository.findById(ID);

        if (result != null) {
            for (Matriz m : matrizList) {
                if (m.isPublicada() && !m.equals(result)) {
                    m.cancelarMatriz();
                    repository.update(m);
                    break;
                }
            }
            String[] dataValores = dataFutura.split("/");

            int year = Integer.parseInt(dataValores[0]);
            int month = Integer.parseInt(dataValores[1]);
            int day = Integer.parseInt(dataValores[2]);
            Date dataFuturaDate = new Date(year, month, day);

            if (dataFuturaDate != null) {
                if (result.publicarMatrizDataFutura(dataFuturaDate)) {
                    return repository.update(result) != null;
                }
            }
        }

        return false;
    }

}
