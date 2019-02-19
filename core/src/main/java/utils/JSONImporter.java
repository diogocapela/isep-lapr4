package utils;

import models.Cobertura;
import models.avaliacao.Caso;
import models.avaliacao.EstadoCaso;
import models.avaliacao.ObjetoSeguro;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import services.CoberturaService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONImporter {

    private CoberturaService coberturaService = new CoberturaService();

    public Caso importCasoFromFile(String filePath) throws Exception {
        JSONParser jsonParser = new JSONParser();
        Object object = null;
        try {
            object = jsonParser.parse(new FileReader(filePath));
        } catch (FileNotFoundException ex) {
            System.out.println("file not found");
            return null;
        }
        JSONObject jsonObject = (JSONObject) object;

        return getCasoFromJSONObject(jsonObject);
    }

    public Caso importCasoFromString(String jsonString) throws Exception {
        JSONParser jsonParser = new JSONParser();
        Object object = null;
        try {
            object = jsonParser.parse(new StringReader(jsonString));
        } catch (FileNotFoundException ex) {
            System.out.println("file not found");
            return null;
        }
        JSONObject jsonObject = (JSONObject) object;

        return getCasoFromJSONObject(jsonObject);
    }

    private Caso getCasoFromJSONObject(JSONObject jsObj) {

        // Estado do Caso
        EstadoCaso estado = EstadoCaso.valueOf((String) jsObj.get(JSONSettings.CASO_ESTADO));

        List<ObjetoSeguro> objetoSeguroList = new ArrayList<>();

        JSONArray objectosSeguradosJSONArray = (JSONArray) jsObj.get(JSONSettings.CASO_OBJECTOS_SEGURADOS);
        Iterator<JSONObject> iteratorObjectosSegurados = objectosSeguradosJSONArray.iterator();
        while (iteratorObjectosSegurados.hasNext()) {
            JSONObject objectoSeguroJSONObject = iteratorObjectosSegurados.next();

            // Descrição do Objecto Seguro
            String descricao = (String) objectoSeguroJSONObject.get(JSONSettings.OBJECTO_SEGURO_DESCRICAO);
            // Latitude do Objecto Seguro
            Double latitude = (Double) objectoSeguroJSONObject.get(JSONSettings.OBJECTO_SEGURO_LATITUDE);
            // Longitude do Objecto Seguro
            Double longitude = (Double) objectoSeguroJSONObject.get(JSONSettings.OBJECTO_SEGURO_LONGITUDE);

            List<Cobertura> coberturaList = new ArrayList<>();

            JSONArray coberturasJSONArray = (JSONArray) objectoSeguroJSONObject.get(JSONSettings.OBJECTO_SEGURO_COBERTURAS);
            Iterator<JSONObject> iteratorCoberturas = coberturasJSONArray.iterator();

            while (iteratorCoberturas.hasNext()) {
                JSONObject coberturaJSONObject = iteratorCoberturas.next();

                // Titulo da Cobertura
                String titulo_cobertura = (String) coberturaJSONObject.get(JSONSettings.COBERTURA_TITULO);

                // Descrição da Cobertura
                String descricao_cobertura = (String) coberturaJSONObject.get(JSONSettings.COBERTURA_DESCRICAO);

                coberturaList.add(coberturaService.createCobertura(titulo_cobertura, descricao_cobertura));
            }
            objetoSeguroList.add(new ObjetoSeguro(descricao, latitude, longitude, coberturaList));

        }

        return new Caso(objetoSeguroList, estado);
    }

}
