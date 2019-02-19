package controllers;

import javafx.util.Pair;
import models.avaliacao.Caso;
import models.avaliacao.ObjetoSeguro;
import models.matriz.Matriz;
import modelsDTO.MatrizDTO;
import repository.MatrizRepository;
import services.AvaliadorService;
import services.GeoReferenceService;
import utils.XMLImporter;

import java.util.LinkedList;
import java.util.List;

public class CompararAvaliacaoDeObjectoFromXMLController {

    private final XMLImporter xmlImporter = new XMLImporter();
    private final MatrizRepository matrizRepository = new MatrizRepository();
    private final GeoReferenceService geoReferenceService = new GeoReferenceService();

    public List<String> comparaAvaliacao(String xmlString) {
        List<String> res = new LinkedList<>();
        //IMPORTAR
        ObjetoSeguro objectoSeguro;
        Pair<Long, Long> idMatrizes;
        try {
            objectoSeguro = xmlImporter.importObjectoSeguroFromString(xmlString);
            idMatrizes = xmlImporter.importIdMatrizesFromString(xmlString);
        } catch (Exception e) {
            e.getCause();
            return null;
        }
        if (objectoSeguro == null || idMatrizes == null) {
            return null;
        }

        // Controi Casos Com Unico Objecto para usar Avaliador Service
        List<ObjetoSeguro> listaUmObjecto = new LinkedList<>();
        listaUmObjecto.add(objectoSeguro);
        Caso casoA = new Caso(listaUmObjecto, null);
        Caso casoB = new Caso(listaUmObjecto, null);

        // Avalia caso com Matriz A
        Matriz matrizA = matrizRepository.findById(idMatrizes.getKey());
        if (matrizA == null || matrizA.isBase()) {
            return null;
        }
        matrizRepository.detach(matrizA);
        MatrizDTO matrizDTO_A = matrizA.toDTO();
        AvaliadorService avaliadorServiceA = new AvaliadorService(casoA, matrizDTO_A, geoReferenceService);
        String demonstracaoA = null;
        Integer indiceRiscoA = null;
        if (avaliadorServiceA.avaliaCaso(casoA, matrizDTO_A, geoReferenceService)) {
            demonstracaoA = casoA.objetosSegurados().get(0).getAvaliacao().toDTO().getDemonstracao();
            indiceRiscoA = casoA.objetosSegurados().get(0).getAvaliacao().toDTO().getIndiceRisco();
        }

        // Avalia caso com Matriz B
        Matriz matrizB = matrizRepository.findById(idMatrizes.getValue());
        if (matrizB == null || matrizB.isBase()) {
            return null;
        }
        matrizRepository.detach(matrizB);
        MatrizDTO matrizDTO_B = matrizB.toDTO();
        AvaliadorService avaliadorServiceB = new AvaliadorService(casoB, matrizDTO_B, geoReferenceService);
        String demonstracaoB = null;
        Integer indiceRiscoB = null;
        if (avaliadorServiceB.avaliaCaso(casoB, matrizDTO_B, geoReferenceService)) {
            demonstracaoB = casoB.objetosSegurados().get(0).getAvaliacao().toDTO().getDemonstracao();
            indiceRiscoB = casoB.objetosSegurados().get(0).getAvaliacao().toDTO().getIndiceRisco();
        }

        // Construir resposta
        if (demonstracaoA != null && indiceRiscoA != null && demonstracaoB != null && indiceRiscoB != null) {

            res.add(objectoSeguro.toDTO().getDescricao());
            res.add(objectoSeguro.toDTO().getLatitude().toString());
            res.add(objectoSeguro.toDTO().getLongitude().toString());
            res.add(idMatrizes.getKey().toString());
            res.add(idMatrizes.getValue().toString());
            res.add(demonstracaoA);
            res.add(indiceRiscoA.toString());
            res.add(demonstracaoB);
            res.add(indiceRiscoB.toString());
            res.add(indiceRiscoA > indiceRiscoB ?
                "O índice de risco é maior com a Matriz A." :
                (indiceRiscoA < indiceRiscoB) ?
                    "O índice de risco é maior com a Matriz B." :
                    "O índice de risco é igual com as duas matrizes.");

        } else {
            return null;
        }

        return res;
    }

}
