package services;

import models.Cobertura;
import models.avaliacao.Caso;
import models.avaliacao.ObjetoSeguro;
import models.matriz.Matriz;
import modelsDTO.CoberturaDTO;
import modelsDTO.ObjetoSeguroDTO;
import repository.CasoRepository;
import repository.MatrizRepository;
import repository.ObjetoSeguroRepository;

import java.util.LinkedList;
import java.util.List;

public class ProcessCasoService {
    private final CasoRepository casoRepository = new CasoRepository();
    private final ObjetoSeguroRepository objetoSeguroRepository = new ObjetoSeguroRepository();
    private final MatrizRepository matrizRepository = new MatrizRepository();
    private GeoReferenceService geoReferenceService;
    private AvaliadorService avaliadorService;

    public Caso processarCaso(Caso caso) {
        if (caso == null) {
            return null;
        }

        Caso casoReal = new Caso();
        for (ObjetoSeguroDTO os : caso.toDTO().getObjetosSegurados()) {
            ObjetoSeguro osTmp = new ObjetoSeguro();
            objetoSeguroRepository.add(osTmp);
            List<Cobertura> coberturas = new LinkedList<>();
            for (CoberturaDTO cDTO : os.getCoberturas()) {
                coberturas.add(new Cobertura(cDTO.getTitulo(), cDTO.getDescricao()));
            }
            ObjetoSeguro objetoSeguro = new ObjetoSeguro(os.getDescricao(), os.getLatitude(), os.getLatitude(), coberturas);
            osTmp.copyAttributes(objetoSeguro);
            objetoSeguroRepository.update(osTmp);
        }

        casoRepository.add(casoReal);
        casoReal.copyAttributes(caso);
        casoRepository.update(casoReal);
        casoReal = casoRepository.findById(casoReal.getID());

        // AVALIAR
        Matriz publicada = matrizRepository.getMatrizPublicada();
        if (publicada == null) {
            return null;
        }
        matrizRepository.detach(publicada);

        geoReferenceService = new GeoReferenceService();

        casoReal.emProcessamento();
        casoRepository.update(casoReal);
        casoReal = casoRepository.findById(casoReal.getID());
        avaliadorService = new AvaliadorService(casoReal, publicada.toDTO(), geoReferenceService);
        boolean isAvaliado = avaliadorService.synchronousRun();

        // dar como processado ou colocar em validacao
        if (isAvaliado) {
            if (casoReal.hasValidacao()) {
                casoReal.emValidacao();
            } else {
                casoReal.processado();
            }
            casoRepository.update(casoReal);
        }

        return casoReal;
    }

}
