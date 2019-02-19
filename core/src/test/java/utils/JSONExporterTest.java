package utils;

import models.Cobertura;
import models.avaliacao.Caso;
import models.avaliacao.EstadoCaso;
import models.avaliacao.ObjetoSeguro;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JSONExporterTest {

    public JSONExporterTest() {
    }

    @Test
    public void test() {
        Cobertura c1 = new Cobertura("Titulo 1", "Descricao 1");
        Cobertura c2 = new Cobertura("Titulo 2", "Descricao 2");
        Cobertura c3 = new Cobertura("Titulo 3", "Descricao 3");

        List<Cobertura> lista1 = new ArrayList<>();
        lista1.add(c1);
        lista1.add(c2);

        List<Cobertura> lista2 = new ArrayList<>();
        lista2.add(c2);
        lista2.add(c3);

        ObjetoSeguro os1 = new ObjetoSeguro("Desc 1", 2.3213, 3.4234423, lista1);
        ObjetoSeguro os2 = new ObjetoSeguro("Desc 2", 4.3213, 10.4234423, lista2);

        List<ObjetoSeguro> objetoSeguros = new ArrayList<>();
        objetoSeguros.add(os1);
        objetoSeguros.add(os2);

        Caso caso = new Caso(objetoSeguros, EstadoCaso.ESPERA);

        JSONExporter jsonExporter = new JSONExporter();

        try {
            jsonExporter.exportCaso(caso, "caso-example.json.log");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
