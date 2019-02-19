package raconsole.controller;

import controllers.AvaliarCasoController;
import models.avaliacao.Caso;
import models.avaliacao.ObjetoSeguro;
import modelsDTO.CasoDTO;
import raconsole.output.AR04Output;
import raconsole.output.AR04OutputDetails;
import raconsole.output.AR04OutputDetails2;
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
import java.util.LinkedList;
import java.util.List;

public class AR04Controller {


    private final static String OUTPUT_XSD = "raconsole/src/main/resources/AR04/AR04_Output.xsd";
    private final static String OUTPUT_XHTML_XSL = "raconsole/src/main/resources/AR04/AR04_Output_XHTML.xsl";

    private static int sequenceFileID = 0;

    private Caso caso;

    private AvaliarCasoController avaliarCasoController = new AvaliarCasoController();
    private CasoRepository casoRepository = new CasoRepository();
    private List<AR04OutputDetails2> detalhesOutput2;
    private AR04OutputDetails detalhesOutput1;

    public void setComent(String comment) {
        caso.setComentario(comment);
    }

    public void saveCaso(Caso caso___) {
        casoRepository.update(caso___);
    }

    public void solicitarReavaliacaoAutomatica(Long idCaso) {
        avaliarCasoController.avaliaCaso(idCaso);
    }

    public Caso getCaso(Long idCaso) {
        caso = casoRepository.findById(idCaso);
        return caso;
    }

    public CasoDTO getCasoDTOFromID(Long idCaso) {
        caso = casoRepository.findById(idCaso);
        CasoDTO casoDTO = caso.toDTO();
        return casoDTO;
    }

    public boolean validarCaso(Long idCaso) {

        Caso caso = casoRepository.findById(idCaso);
        if (caso.isEmValidacao()) {
            caso.processado();
            return (casoRepository.update(caso) != null);
        } else {
            return false;
        }
    }

    public String exportarCasoXHTML(String destinationDirectory, Long idCaso) {
        if (destinationDirectory == null) {
            return null;
        }
        Caso caso = casoRepository.findById(idCaso);
        detalhesOutput2 = new LinkedList<>();
        CasoDTO casoDTO = caso.toDTO();
        for (ObjetoSeguro obj : caso.objetosSegurados()) {
            AR04OutputDetails2 outputDetails = new AR04OutputDetails2(obj.getDescricao(), obj.getLatitude(), obj.getLongitude());
            System.out.println(outputDetails);
            detalhesOutput2.add(outputDetails);
        }
        detalhesOutput1 = new AR04OutputDetails(detalhesOutput2);

        AR04Output output = new AR04Output(idCaso, detalhesOutput1, casoDTO.getEstado());
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
        File xhtmlFile = fileUtils.createFile(xslResult, String.format(destinationDirectory + "/AR04_%d.xhtml", sequenceFileID++));
        if (xhtmlFile == null) {
            return null;
        } else {
            return xhtmlFile.getAbsolutePath();
        }
    }

    private String buildXMLString(AR04Output o) {
        try {
            String result;
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            JAXB.marshal(o, sw);
            result = sw.toString();
            result = result.replace("<caso>", "<caso xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">");
            System.out.println(result);
            return result;

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }


}
