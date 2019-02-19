package raconsole.controller;

import models.avaliacao.Caso;
import modelsDTO.CasoDTO;
import raconsole.output.AR05Output;
import raconsole.output.AR05OutputDetails;
import raconsole.output.AR05OutputDetails2;
import raconsole.output.AR05OutputSumario;
import repository.CasoRepository;
import utils.validate_transform.FileUtils;
import utils.validate_transform.XSDValidator;
import utils.validate_transform.XSLTransform;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.StringWriter;
import java.util.*;

public class AR05Controller {

    private final static String OUTPUT_XSD = "raconsole/src/main/resources/AR05/AR05_Output.xsd";
    private final static String OUTPUT_XHTML_XSL = "raconsole/src/main/resources/AR05/AR05_Output_XHTML.xsl";
    private static int sequenceFileID = 0;
    private CasoRepository casoRepository = new CasoRepository();
    private AR05OutputSumario detalhesOutputSumario;
    private List<AR05OutputDetails2> detalhesOutput2 = new ArrayList<>();
    private AR05OutputDetails detalhesOutput;

    /**
     * Método que preenche e devolve uma Linked List de Strings com os pedidos Validados pelo Analista.
     *
     * @param nomeAnalista
     * @return
     */
    public List<String> getCasosValidadosPorAnalista(String nomeAnalista) {

        List<String> resultadoUI = new LinkedList<>();

        //Vai buscar Casos validados;
        List<Caso> listaCasosValidados = casoRepository.fetchCasosValidadosPorAnalista(nomeAnalista);
        for (Caso tempCaso : listaCasosValidados) {
            CasoDTO tempCasoDTO = tempCaso.toDTO();
            // id + getTotalAvaliacao() + data pedido validacao;
            String tmpString = String.format("%s, %s, %s, %s, %s", "O caso com o id: " + tempCasoDTO.getID(), "tem o indície de risco de ",
                tempCaso.getTotalAvaliacao(), "e foi validado em ", tempCasoDTO.getValidacaoDTO().getDataConclusao());
            resultadoUI.add(tmpString);
        }
        return resultadoUI;
    }

    public String formatDTO(CasoDTO casoDTO) {
        String tmpString = String.format("O caso com o id: %d e foi validado em %s ", casoDTO.getID(),
            casoDTO.getValidacaoDTO().getDataConclusao());
        return tmpString;

    }

    public List<CasoDTO> ordenarPedidosMaisRecentes(String nomeAnalista) {
        List<Caso> listaCasosValidados = casoRepository.fetchCasosValidadosPorAnalista(nomeAnalista);
        Collections.sort(listaCasosValidados);
        Collections.reverse(listaCasosValidados);
        ArrayList<CasoDTO> listaCasosDTO = new ArrayList<>();
        for (Caso caso : listaCasosValidados) {
            listaCasosDTO.add(caso.toDTO());
        }
        return listaCasosDTO;
    }

    public Long tempoOcorridoEntreDataConclusaoEDataAtribuido(Long idCaso) {
        Caso caso = casoRepository.findById(idCaso);
        return caso.tempoOcorridoEntreDataConclusaoEDataAtribuido();
    }


    public String constroiSumarioPedidosApresentados(List<CasoDTO> casosDTOAnalista) {
        String sumario = "";
        sumario += ("\nSUMÁRIO:\n");
        sumario += "===================================================================================\n\n";
        Long tempoMedio = getTempoMedioAnaliseComDTO(casosDTOAnalista);
        if (tempoMedio != null) {
            sumario += "Total de casos: " + casosDTOAnalista.size() + "\n";
            sumario += "Tempo médio de análise ";
            sumario += tempoMedio + " segundos\n";
        } else {
            sumario += "\nEste analista nao tem casos";
        }
        return sumario;
    }


    public Long getTempoMedioAnaliseComDTO(List<CasoDTO> casosDTOAnalista) {
        Long tempoMedio = 0l;
        if (!casosDTOAnalista.isEmpty()) {
            Long somaTempos = 0l;
            for (CasoDTO casoDTO : casosDTOAnalista) {
                Caso caso = casoRepository.findById(casoDTO.getID());
                somaTempos += caso.tempoOcorridoEntreDataConclusaoEDataAtribuido();
            }
            tempoMedio = somaTempos / casosDTOAnalista.size();
        } else {
            return 0L;
        }
        return tempoMedio;
    }

    public Long getTempoMedioAnalise(List<Caso> casosAnalista) {
        Long tempoMedio = 0l;
        if (!casosAnalista.isEmpty()) {
            Long somaTempos = 0l;
            for (Caso caso : casosAnalista) {
                somaTempos += caso.tempoOcorridoEntreDataConclusaoEDataAtribuido();
            }
            tempoMedio = somaTempos / casosAnalista.size();
        } else {
            return 0L;
        }
        return tempoMedio;
    }

    public List<CasoDTO> filtrarCasosPeriodoTempo(Integer ano, Integer mesInicial, Integer diaInicial, Integer mesFinal, Integer diaFinal,
                                                  List<CasoDTO> casosDTO) {
        List<CasoDTO> casosDTOFiltrados = new ArrayList<>();
        Date dataInicial = constroiData(ano, mesInicial, diaInicial);
        Date dataFinal = constroiData(ano, mesFinal, diaFinal);
        for (CasoDTO tempCasoDTO : casosDTO) {
            Caso caso = casoRepository.findById(tempCasoDTO.getID());
            if (caso.getDataConclusao().compareTo(dataInicial) > 0 && caso.getDataConclusao().compareTo(dataFinal) < 0) {
                casosDTOFiltrados.add(tempCasoDTO);
            }
        }
        return casosDTO;
    }

    public Date constroiData(Integer ano, Integer mes, Integer dia) {
        Date date = new Date();
        date.setYear(ano);
        date.setMonth(mes);
        date.setDate(dia);
        return date;
    }

    public String exportarResultadoConsultaXHTML(String destinationDirectory, String username) {
        if (destinationDirectory == null) {
            return null;
        }
        List<Caso> casosAnalista = casoRepository.fetchCasosValidadosPorAnalista(username);
        Long tempoMedio = getTempoMedioAnalise(casosAnalista);
        detalhesOutput2 = new LinkedList<>();

        detalhesOutputSumario = new AR05OutputSumario(casosAnalista.size(), tempoMedio);
        for (Caso caso : casosAnalista) {
            Long id = caso.getID();
            Integer indiceRisco = caso.getTotalAvaliacao();
            String dataValidacao = caso.getDataConclusao().toString();
            Long tempoValidacao = caso.tempoOcorridoEntreDataConclusaoEDataAtribuido();
            AR05OutputDetails2 detail = new AR05OutputDetails2(id, indiceRisco, dataValidacao, tempoValidacao);
            if (detail != null) {
                detalhesOutput2.add(detail);
            }
        }

        detalhesOutput = new AR05OutputDetails(detalhesOutput2);

        AR05Output output = new AR05Output(username, detalhesOutput, detalhesOutputSumario);

        String outputXMLString = buildXMLString(output);

        // validate against XSD
        try {
            new XSDValidator(outputXMLString, OUTPUT_XSD).validateXMLwithXSD();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // transform with XSL
        String xslResult = null;
        try {
            xslResult = new XSLTransform(outputXMLString, OUTPUT_XHTML_XSL).transformXMLwithXSL();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        FileUtils fileUtils = new FileUtils();
        File xhtmlFile = fileUtils.createFile(xslResult, String.format(destinationDirectory + "/AR05_%d.xhtml", sequenceFileID++));
        if (xhtmlFile == null) {
            return null;
        } else {
            return xhtmlFile.getAbsolutePath();
        }
    }

    private String buildXMLString(AR05Output o) {
        try {
            String result;
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            JAXB.marshal(o, sw);
            result = sw.toString();
            result = result.replace("<casosValidados>", "<caso xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">");
            System.out.println(result);
            return result;

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
