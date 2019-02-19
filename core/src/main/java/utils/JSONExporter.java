package utils;

import models.avaliacao.Caso;
import modelsDTO.AvaliacaoDTO;
import modelsDTO.CoberturaDTO;
import modelsDTO.ObjetoSeguroDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class JSONExporter {

    public void exportCaso(Caso caso, String filePath) throws Exception {
        // Parse into JSON
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSONSettings.CASO_ESTADO, caso.toDTO().getEstado());
        JSONArray objetosSeguradosArray = new JSONArray();
        for (ObjetoSeguroDTO objetoSeguro : caso.toDTO().getObjetosSegurados()) {
            JSONObject objetoSeguroObject = new JSONObject();
            objetoSeguroObject.put(JSONSettings.OBJECTO_SEGURO_DESCRICAO, objetoSeguro.getDescricao());
            objetoSeguroObject.put(JSONSettings.OBJECTO_SEGURO_LATITUDE, objetoSeguro.getLatitude());
            objetoSeguroObject.put(JSONSettings.OBJECTO_SEGURO_LONGITUDE, objetoSeguro.getLongitude());
            JSONArray coberturasArray = new JSONArray();
            for (CoberturaDTO cobertura : objetoSeguro.getCoberturas()) {
                JSONObject coberturaObject = new JSONObject();
                coberturaObject.put(JSONSettings.COBERTURA_TITULO, cobertura.getTitulo());
                coberturaObject.put(JSONSettings.COBERTURA_DESCRICAO, cobertura.getDescricao());
                coberturasArray.add(coberturaObject);
            }
            objetoSeguroObject.put(JSONSettings.OBJECTO_SEGURO_COBERTURAS, coberturasArray);
            objetosSeguradosArray.add(objetoSeguroObject);
            AvaliacaoDTO avaliacao = objetoSeguro.getAvaliacao();
            if (avaliacao != null) {
                JSONObject avaliacaoObject = new JSONObject();
                avaliacaoObject.put(JSONSettings.AVALIACAO_INDICE_RISCO, avaliacao.getIndiceRisco());
                avaliacaoObject.put(JSONSettings.AVALIACAO_DEMONSTRACAO, avaliacao.getDemonstracao());
                objetoSeguroObject.put(JSONSettings.OBJECTO_SEGURO_AVALIACAO, avaliacaoObject);
            }
        }
        jsonObject.put(JSONSettings.CASO_OBJECTOS_SEGURADOS, objetosSeguradosArray);

        // Save into JSON file
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(jsonObject.toJSONString());
        writer.close();
    }

}
