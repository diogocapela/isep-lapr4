package controllers;

import javafx.util.Pair;
import models.avaliacao.Caso;
import models.avaliacao.ObjetoSeguro;
import models.matriz.Matriz;
import modelsDTO.AvaliacaoDTO;
import print.BuildPrintAvaliacao;
import repository.CasoRepository;
import repository.MatrizRepository;
import services.AvaliadorService;
import services.GeoReferenceService;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AvaliarCasoController {

    private CasoRepository casoRepository = new CasoRepository();
    private MatrizRepository matrizRepository = new MatrizRepository();
    private BuildPrintAvaliacao buildPrintAvaliacao = new BuildPrintAvaliacao();
    private GeoReferenceService geoReferenceService;
    private AvaliadorService avaliadorService;


    public String avaliaCaso(Long idCaso) {
        String resultUI = "";
        Caso caso = null;
        Matriz publicada = matrizRepository.getMatrizPublicada();
        if (publicada == null) {
            return resultUI;
        }
        matrizRepository.detach(publicada);

        boolean valid = false;

        if (idCaso != null) {
            matrizRepository.detach(publicada);
            caso = casoRepository.findById(idCaso);
            if (caso != null) {
                valid = true;
                caso.emProcessamento();
                casoRepository.update(caso);
                geoReferenceService = new GeoReferenceService();
                avaliadorService = new AvaliadorService(caso, publicada.toDTO(), geoReferenceService);
                avaliadorService.run();
                caso.processado();
                casoRepository.update(caso);
            }
        }

        if (!valid) {
            return resultUI;
        }

        resultUI = buildPrintAvaliacao.buildResult(caso, publicada.getMatrizID());

        return resultUI;
    }

    public boolean avaliaCasosBatch() {
        // get the runtime object associated with the current Java application
        Runtime runtime = Runtime.getRuntime();
        // get the number of processors available to the Java virtual machine
        int numberProcessors = runtime.availableProcessors();

        // get published Matrix
        Matriz matriz = matrizRepository.getMatrizPublicada();
        if (matriz == null) {
            return false;
        }
        matrizRepository.detach(matriz);

        // obtained casos to process
        List<Caso> casosProcessaveis = casoRepository.fetchCasesNotProcessing();
        final int casosSize = casosProcessaveis.size();
        int sequenceID = 0;

        // constructs avaliadores
        AvaliadorService[] avaliadores = new AvaliadorService[casosSize];
        geoReferenceService = new GeoReferenceService();
        for (Caso casoTmp : casosProcessaveis) {
            casoTmp.emProcessamento();
            casoRepository.update(casoTmp);
            avaliadores[sequenceID++] = new AvaliadorService(casoTmp, matriz.toDTO(), geoReferenceService);
        }

        // runs in multithreading
        ExecutorService pool = Executors.newFixedThreadPool(numberProcessors);
        for (AvaliadorService ac : avaliadores) {
            ac.run();
        }
        pool.shutdown();
        try {
            pool.awaitTermination(Short.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            return false;
        }

        // after every Avaliador run
        for (Caso c : casosProcessaveis) {
            c.processado();
            casoRepository.update(c);
        }

        return true;
    }

    public String avaliaCasoCom2Matrizes(Long idCaso, Pair<Long, Long> idMatrizes) {
        Boolean result1 = false;
        Boolean result2 = false;
        String resultUI = "";
        Caso caso1 = null;
        Caso caso2 = null;
        Matriz matriz1 = null;
        Matriz matriz2 = null;
        List<AvaliacaoDTO> listaDemoCaso1 = new LinkedList<>();
        List<AvaliacaoDTO> listaDemoCaso2 = new LinkedList<>();

        // fetch caso
        if (idCaso == null) {
            return resultUI;
        }
        caso1 = casoRepository.findById(idCaso);
        caso2 = casoRepository.findById(idCaso);
        if (caso1 == null || caso2 == null) {
            return resultUI;
        }
        casoRepository.detach(caso1);
        casoRepository.detach(caso2);

        //fetch matrizes
        if (idMatrizes == null) {
            return resultUI;
        }
        matriz1 = matrizRepository.findById(idMatrizes.getKey());
        matriz2 = matrizRepository.findById(idMatrizes.getValue());
        if (matriz1 == null || matriz2 == null) {
            return resultUI;
        }
        matrizRepository.detach(matriz1);
        matrizRepository.detach(matriz2);

        geoReferenceService = new GeoReferenceService();

        avaliadorService = new AvaliadorService(caso1, matriz1.toDTO(), geoReferenceService);
        avaliadorService.run();
        result1 = caso1.toDTO().getObjetosSegurados().get(0).getAvaliacao() != null;
        for (ObjetoSeguro obj : caso1.objetosSegurados()) {
            listaDemoCaso1.add(obj.toDTO().getAvaliacao());
        }

        avaliadorService = new AvaliadorService(caso2, matriz2.toDTO(), geoReferenceService);
        avaliadorService.run();
        result2 = caso2.toDTO().getObjetosSegurados().get(0).getAvaliacao() != null;
        for (ObjetoSeguro obj : caso2.objetosSegurados()) {
            listaDemoCaso2.add(obj.toDTO().getAvaliacao());
        }

        if (result1 && result2) {
            resultUI = buildPrintAvaliacao.buildComparedResult(caso1.toDTO(), listaDemoCaso1, listaDemoCaso2,
                idMatrizes.getKey(), idMatrizes.getValue());
        }

        return resultUI;
    }
}
