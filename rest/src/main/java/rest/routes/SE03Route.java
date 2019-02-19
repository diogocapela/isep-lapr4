package rest.routes;

import models.avaliacao.Caso;
import modelsDTO.CasoDTO;
import repository.CasoRepository;
import rest.HTTPMessage;
import rest.Settings;
import rest.output.SE03Output;
import rest.output.SE03OutputRoot;
import utils.validate_transform.XSDValidator;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


public class SE03Route extends Route {
    private final static String INPUT_XSD = "rest/src/main/resources/SE03/SE03_Input.xsd";
    private final static String OUTPUT_XSD = "rest/src/main/resources/SE03/SE03_Output.xsd";
    private final static String OUTPUT_XHTML_XSL = "rest/src/main/resources/SE03/SE03_Output_XHTML.xsl";
    private final static String OUTPUT_JSON_XSL = "rest/src/main/resources/SE03/SE03_Output_JSON.xsl";

    public SE03Route(HTTPMessage request, HTTPMessage response) throws IOException {
        super(request, response);
    }

    /*Sem filtros*/

    public void resultadoAvaliacaoDeRiscoConcluidos() {
        String endpoint = request.getURI();
        SE03OutputRoot output = null;
        SE03Output out = new SE03Output();
        String postbody = new String(request.getContent());
        String[] params = endpoint.split("/");
        if (params.length < 1) {
            response.setResponseStatus(Settings.STATUS_NOT_FOUND);
            output = new SE03OutputRoot("Erro nos parametros do URL!");
            out.addOutput(output);
        } else {
            List<CasoDTO> listCasosDTO = new ArrayList<>();
            String[] pGetArray = null;
            try {
                if (postbody != null)
                    pGetArray = postbody.split("&");
            } catch (Exception ex) {

            }
            String city = null;
            String from = null;
            String to = null;
            if (pGetArray != null) {
                for (String s : pGetArray) {
                    //var get name
                    String var = s.split("=")[0];
                    //var get value
                    String value = null;
                    if (s.split("=").length >= 2)
                        value = s.split("=")[1];
                    if ("from".equalsIgnoreCase(var)) {
                        from = value;
                    }
                    if ("to".equalsIgnoreCase(var)) {
                        to = value;
                    }
                    if ("city".equalsIgnoreCase(var)) {
                        city = value;
                    }
                }
            }

            if (city == null && from == null && to == null)
                listCasosDTO = (new CasoRepository()).fetchCasesProceeded();
            else if (city != null && from == null && to == null) {
                //it must be the city
                listCasosDTO = (new CasoRepository()).fetchCasesProceeded(city);
            } else if (from != null && to != null && city == null) {
                listCasosDTO = (new CasoRepository()).fetchCasesProceeded(from, to);
            } else if (city != null && to != null && from != null) {
                listCasosDTO = (new CasoRepository()).fetchCasesProceeded(from, to, city);
            }
            if (listCasosDTO.isEmpty()) {
                response.setResponseStatus(Settings.STATUS_NOT_FOUND);
                output = new SE03OutputRoot("Sem casos processados!");
                out.addOutput(output);
            } else {
                response.setResponseStatus(Settings.STATUS_OK);

                for (CasoDTO cdto : listCasosDTO) {
                    output = new SE03OutputRoot("Sucesso");
                    String estado = cdto.getEstado();
                    Caso tempCaso = (new CasoRepository()).findById(cdto.getID());
                    output.SE03OutputAddCasoInfo(cdto);
                    output.SE03OutputAddAvaliacao(tempCaso.getTotalAvaliacao());
                    output.SE03OutputAddEstado(estado);
                    out.addOutput(output);
                }
            }
        }

        String xmlOutputString = buildXMLString(out);

        boolean invalidX = false;
        try {
            invalidX = !(new XSDValidator(xmlOutputString, OUTPUT_XSD).validateXMLwithXSD());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (invalidX) {
            response.setResponseStatus(Settings.STATUS_BAD_REQUEST);
            response.setContentFromString(formatUtils.buildOutputString("ERROR validating raconsole.output XML against XSD", getXSLErrorFileName()), Settings.xmlOutputFormat);
            return;
        }
        response.setResponseStatus(Settings.STATUS_OK);
        String outputString = formatUtils.buildOutputString(xmlOutputString, getXSLFileName());
        response.setContentFromString(outputString, outputFormat);
    }


    private String buildXMLString(SE03Output o) {
        try {
            String result;
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            JAXB.marshal(o, sw);
            result = sw.toString();
            result = result.replace("<root>", "<root xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">");
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
