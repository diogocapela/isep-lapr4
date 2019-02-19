package services;

import javafx.util.Pair;
import models.Cobertura;
import models.Envolvente;
import models.FatorRisco;
import models.Rating;
import models.avaliacao.*;
import modelsDTO.CelulaBaseDTO;
import modelsDTO.CelulaDetalhadaDTO;
import modelsDTO.MatrizDTO;
import repository.EnvolventeRepository;
import repository.FatorRiscoRepository;
import settings.Settings;

import java.util.LinkedList;
import java.util.List;

public class AvaliadorService implements AvaliadorServiceInterface, Runnable {

    private Caso caso;
    private MatrizDTO matriz;
    private GeoReferenceService geoReferenceService;
    private EnvolventeRepository er = new EnvolventeRepository();
    private FatorRiscoRepository frr = new FatorRiscoRepository();

    public AvaliadorService(Caso caso, MatrizDTO matriz, GeoReferenceService geoReferenceService) {
        this.caso = caso;
        this.matriz = matriz;
        this.geoReferenceService = geoReferenceService;
    }

    @Override
    public void run() {
        avaliaCaso(this.caso, this.matriz, this.geoReferenceService);
        //System.out.println(caso);
    }

    public boolean synchronousRun() {
        return avaliaCaso(this.caso, this.matriz, this.geoReferenceService);
    }

    @Override
    public Boolean avaliaCaso(Caso caso, MatrizDTO matriz, GeoReferenceService geoReferenceService) {
        try {
            List<ObjetoSeguro> objetoSeguros = caso.objetosSegurados();

            for (ObjetoSeguro obj : objetoSeguros) {
                caso.atribuirAvaliacao(obj, avaliaObjeto(obj, matriz, geoReferenceService));
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private Avaliacao avaliaObjeto(ObjetoSeguro obj, MatrizDTO matriz, GeoReferenceService geoReferenceService) {
        Avaliacao avaliacao;
        List<Cobertura> coberturasDoObj = obj.coberturasDoObjeto();
        Pair<Double, Double> coordenadas = obj.coordenadas();
        List<CelulaBaseDTO> celulasDetalhadasByCobertura;
        List<AvaliacaoCelular> avaliacoesCelulares = new LinkedList<>();
        Integer valorGeo;
        Rating escalaGeo;
        AvaliacaoCelular avaliacaoCelular;

        for (Cobertura cob : coberturasDoObj) {
            celulasDetalhadasByCobertura = matriz.getCelulasByCoberturaToDTO(cob);
            for (CelulaBaseDTO celulaDTO : celulasDetalhadasByCobertura) {
                CelulaDetalhadaDTO celulaTmpDTO = (CelulaDetalhadaDTO) celulaDTO;
                Envolvente geoEnv = new Envolvente(celulaTmpDTO.getEnvolventeDTO().getTitulo(), celulaTmpDTO.getEnvolventeDTO().getDescricao(),
                    celulaTmpDTO.getEnvolventeDTO().getLatitude(), celulaTmpDTO.getEnvolventeDTO().getLongitude());
                FatorRisco geoFR = new FatorRisco(celulaTmpDTO.getFatorRiscoDTO().getTitulo(), celulaTmpDTO.getFatorRiscoDTO().getDescricao());

                escalaGeo = geoReferenceService.getGeoRating(geoEnv, geoFR, coordenadas.getKey(), coordenadas.getValue());

                er.detach(geoEnv);
                frr.detach(geoFR);
                valorGeo = getValorEscala(escalaGeo, celulaTmpDTO);
                avaliacaoCelular = getAvaliacaoCelular(celulaDTO, valorGeo);
                avaliacoesCelulares.add(avaliacaoCelular);
            }
        }

        avaliacao = getAvaliacaoObjeto(avaliacoesCelulares);

        return avaliacao;
    }


    private AvaliacaoCelular getAvaliacaoCelular(CelulaBaseDTO celulaDTO, Integer valorGeo) {
        CelulaDetalhadaDTO celulaTmpDTO = (CelulaDetalhadaDTO) celulaDTO;
        Integer peso = celulaTmpDTO.getPeso().getPeso();
        Integer scoreMaximo = celulaTmpDTO.getEscala().getEscalaElevada();

        return new AvaliacaoCelular(peso, valorGeo, scoreMaximo,
            celulaTmpDTO.getCoberturaDTO().getDescricao(),
            celulaTmpDTO.getFatorRiscoDTO().getTitulo() + " a " + celulaTmpDTO.getEnvolventeDTO().getDescricao());
    }

    /**
     * Método que devolve o valor da escala referente à classificação obtida
     * pelo GeoReferenceService
     */
    private Integer getValorEscala(Rating geoRating, CelulaDetalhadaDTO celulaDTO) {
        if (geoRating == null) {
            if (celulaDTO.getNecessidade().getNecessidade().equalsIgnoreCase(Settings.FACULTATIVO)) {
                return 0;
            } else if (celulaDTO.getContribuicao().getContribuicao().equalsIgnoreCase(Settings.POSITIVA)) {
                return celulaDTO.getEscala().getEscalaBaixa();
            } else if (celulaDTO.getContribuicao().getContribuicao().equalsIgnoreCase(Settings.NEGATIVA)) {
                return celulaDTO.getEscala().getEscalaElevada();
            }
        } else {
            if (geoRating.equals(Rating.LOW)) {
                return celulaDTO.getEscala().getEscalaBaixa();
            } else if (geoRating.equals(Rating.MEDIUM)) {
                return celulaDTO.getEscala().getEscalaMedia();
            } else {
                return celulaDTO.getEscala().getEscalaElevada();
            }
        }

        return celulaDTO.getEscala().getEscalaElevada();
    }

    private Avaliacao getAvaliacaoObjeto(List<AvaliacaoCelular> avaliacoesCelulares) {
        Integer scoreMaximo = 0;
        Integer scoreObtido = 0;
        StringBuilder demontracaoObtida = new StringBuilder();
        StringBuilder demontracaoMaxima = new StringBuilder();

        if (avaliacoesCelulares.size() > 0) {
            for (AvaliacaoCelular avCel : avaliacoesCelulares) {
                scoreMaximo += avCel.getScoreMaximo();
                scoreObtido += avCel.getScoreObtido();
                demontracaoObtida.append(avCel.getCelula()).append(" - ").append(avCel.getDemonstracaoObtida()).append("\n");
                demontracaoMaxima.append(avCel.getCelula()).append(" - ").append(avCel.getDemonstracaoMaxima()).append("\n");
            }
            demontracaoObtida.deleteCharAt(demontracaoObtida.length() - 1);
            demontracaoMaxima.deleteCharAt(demontracaoMaxima.length() - 1);

            Integer indiceRisco = null;
            if (scoreMaximo != 0) {
                indiceRisco = Math.round(((float) scoreObtido / (float) scoreMaximo) * 100);
            } else {
                indiceRisco = 100;
            }

            return new Avaliacao(
                new IndiceRisco(indiceRisco),
                new Demonstracao("\nScore Obtido:" +
                    "\n------------\n" + demontracaoObtida + "\n" +
                    "\nScore Máximo:" +
                    "\n------------\n" + demontracaoMaxima + "\n")
            );
        }
        return new Avaliacao(
            new IndiceRisco(100),
            new Demonstracao("Sem dados suficientes")
        );
    }

}
