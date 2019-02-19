package rest.routes;

import controllers.GetCasoController;
import models.avaliacao.Caso;
import models.avaliacao.EstadoCaso;
import modelsDTO.CasoDTO;
import repository.CasoRepository;
import rest.HTTPMessage;
import rest.Settings;
import rest.output.SE02Output;
import utils.validate_transform.XSDValidator;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;

public class SE02Route extends Route {

    private final static String OUTPUT_XSD = "rest/src/main/resources/SE02/SE02_Output.xsd";
    private final static String OUTPUT_XHTML_XSL = "rest/src/main/resources/SE02/SE02_Output_XHTML.xsl";
    private final static String OUTPUT_JSON_XSL = "rest/src/main/resources/SE02/SE02_Output_JSON.xsl";

    public SE02Route(HTTPMessage request, HTTPMessage response) throws IOException {
        super(request, response);
    }

    public void resultadoAvaliacaoDeRisco() {
        String endpoint = request.getURI();
        SE02Output output;

        String[] params = endpoint.split("/");
        if (params.length < 3) {
            response.setResponseStatus(Settings.STATUS_NOT_FOUND);
            output = new SE02Output("Erro nos parametros do URL!");
        } else {
            String idCaso = params[2];
            CasoDTO casoDTO = new GetCasoController().getCasoByID(idCaso);
            if (casoDTO == null) {
                response.setResponseStatus(Settings.STATUS_NOT_FOUND);
                output = new SE02Output("Caso com o ID " + idCaso + " não encontrado!");
            } else {
                String estado = casoDTO.getEstado();
                response.setResponseStatus(Settings.STATUS_OK);
                if (estado.equalsIgnoreCase(EstadoCaso.PROCESSADO.toString())) {
                    CasoRepository casoRepository = new CasoRepository();
                    Caso tempCaso = casoRepository.findById(Long.parseLong(idCaso));

                    output = new SE02Output("Caso com o ID " + idCaso + " está no estado " + estado + " e o seu indice de risco é " + tempCaso.getTotalAvaliacao());

                } else {
                    output = new SE02Output("Caso com o ID " + idCaso + " está no estado " + estado);
                }
            }
        }

        String xmlOutputString = buildXMLString(output);

        // validate raconsole.output in XML
        boolean invalidXML = false;
        try {
            invalidXML = !(new XSDValidator(xmlOutputString, OUTPUT_XSD).validateXMLwithXSD());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (invalidXML) {
            response.setResponseStatus(Settings.STATUS_BAD_REQUEST);
            response.setContentFromString(formatUtils.buildOutputString("ERROR validating raconsole.output XML against XSD", getXSLErrorFileName()), Settings.xmlOutputFormat);
            return;
        }
        response.setResponseStatus(Settings.STATUS_OK);
        String outputString = formatUtils.buildOutputString(xmlOutputString, getXSLFileName());
        response.setContentFromString(outputString, outputFormat);
    }

    private String buildXMLString(SE02Output o) {
        try {
            String result;
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            JAXB.marshal(o, sw);
            result = sw.toString();
            result = result.replace("<raconsole.output>", "<raconsole.output xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">");
            //System.out.println(result);
            return result;

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getXSLFileName() {
        if (outputFormat.equals(Settings.xmlOutputFormat)) {
            return null;
        }

        if (outputFormat.equals(Settings.xhtmlOutputFormat)) {
            return OUTPUT_XHTML_XSL;
        } else if (outputFormat.equals(Settings.jsonOutputFormat)) {
            return OUTPUT_JSON_XSL;
        }

        return null;
    }
}
