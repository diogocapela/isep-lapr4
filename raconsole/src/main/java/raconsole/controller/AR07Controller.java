package raconsole.controller;

import controllers.ListEnvolventesController;
import georef.utils.LocationInfos;
import modelsDTO.EnvolventeDTO;
import raconsole.output.AR07Output;
import raconsole.output.AR07OutputDetails;
import utils.validate_transform.FileUtils;
import utils.validate_transform.XSDValidator;
import utils.validate_transform.XSLTransform;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class AR07Controller {

    private final static String OUTPUT_XSD = "raconsole/src/main/resources/AR07/AR07_Output.xsd";
    private final static String OUTPUT_XHTML_XSL = "raconsole/src/main/resources/AR07/AR07_Output_XHTML.xsl";

    private static int sequenceFileID = 0;

    // filtro area retangular
    private Double latitudeMin;
    private Double longitudeMin;
    private Double latitudeMax;
    private Double longitudeMax;
    // filtro distrito
    private String distrito;

    // pronto a exportar
    private List<AR07OutputDetails> detalhesOutput;

    public AR07Controller(Double latitude1, Double longitude1, Double latitude2, Double longitude2, String distrito) {
        if (latitude1 != null && longitude1 != null && latitude2 != null && longitude2 != null) {
            if (latitude1 <= latitude2) {
                this.latitudeMin = latitude1;
                this.latitudeMax = latitude2;
            } else {
                this.latitudeMin = latitude2;
                this.latitudeMax = latitude1;
            }
            if (longitude1 <= longitude2) {
                this.longitudeMin = longitude1;
                this.longitudeMax = longitude2;
            } else {
                this.longitudeMin = longitude2;
                this.longitudeMax = longitude1;
            }
        } else {
            latitudeMin = null;
            latitudeMax = null;
            longitudeMin = null;
            longitudeMax = null;
        }
        this.distrito = distrito;
    }

    public List<String> listarEnvolventesRegistadas() {
        List<String> result = new LinkedList<>();
        this.detalhesOutput = new LinkedList<>();

        ListEnvolventesController listEnvolventesController = new ListEnvolventesController();
        List<EnvolventeDTO> envolventesResultList = listEnvolventesController.listEnvolventes();

        // excluir os que não pertençam à área retangular
        envolventesResultList = filtrarCoordenadas(envolventesResultList);
        if (envolventesResultList == null) {
            return null;
        }

        // excluir os que não pertençam ao distrito
        filtrarDistrito(envolventesResultList);

        Comparator<EnvolventeDTO> comparator = new Comparator<EnvolventeDTO>() {
            @Override
            public int compare(EnvolventeDTO e1, EnvolventeDTO e2) {
                if (e1.getTitulo().compareTo(e2.getTitulo()) == 0) {
                    return e1.getDescricao().compareTo(e2.getDescricao());
                } else {
                    return e1.getTitulo().compareTo(e2.getTitulo());
                }
            }
        };

        // sort by tipo de envolvente
        Collections.sort(envolventesResultList, comparator);

        for (EnvolventeDTO envDTO : envolventesResultList) {
            AR07OutputDetails odTmp = new AR07OutputDetails(
                LocationInfos.getDistrictFromCoordinates(envDTO.getLatitude(), envDTO.getLongitude()),
                envDTO.getTitulo(), envDTO.getDescricao(), envDTO.getLatitude(), envDTO.getLongitude()
            );

            this.detalhesOutput.add(odTmp);
            result.add(odTmp.toString());
        }

        return result;
    }

    private List<EnvolventeDTO> filtrarCoordenadas(List<EnvolventeDTO> envolventesResultList) {
        List<EnvolventeDTO> envolventesIteratorList = new LinkedList<>(envolventesResultList);

        if (latitudeMin != null && latitudeMax != null && longitudeMin != null && longitudeMax != null) {
            for (EnvolventeDTO envDTO : envolventesIteratorList) {
                if (envDTO.getLatitude() < latitudeMin ||
                    envDTO.getLatitude() > latitudeMax ||
                    envDTO.getLongitude() < longitudeMin ||
                    envDTO.getLongitude() > longitudeMax) {
                    envolventesResultList.remove(envDTO);
                }
            }
        } else if (!(latitudeMin == null && latitudeMax == null && longitudeMin == null && longitudeMax == null)) {
            // impossivel calcular área retangular
            return null;
        }

        return envolventesResultList;
    }

    private void filtrarDistrito(List<EnvolventeDTO> envolventesResultList) {
        List<EnvolventeDTO> envolventesIteratorList = new LinkedList<>(envolventesResultList);

        if (distrito != null && !distrito.equals("")) {
            for (EnvolventeDTO envDTO : envolventesIteratorList) {
                String distritoTmp = LocationInfos.getDistrictFromCoordinates(envDTO.getLatitude(), envDTO.getLongitude());
                if (!distrito.equalsIgnoreCase(distritoTmp)) {
                    envolventesResultList.remove(envDTO);
                }
            }
        }
    }

    public String exportarEnvolventesXHTML(String destinationDirectory) {
        if (destinationDirectory == null) {
            return null;
        }

        AR07Output output = new AR07Output(detalhesOutput);
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
        File xhtmlFile = fileUtils.createFile(xslResult, String.format(destinationDirectory + "/AR07_%d.xhtml", sequenceFileID++));
        if (xhtmlFile == null) {
            return null;
        } else {
            return xhtmlFile.getAbsolutePath();
        }
    }

    private String buildXMLString(AR07Output o) {
        try {
            String result;
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            JAXB.marshal(o, sw);
            result = sw.toString();
            result = result.replace("<envolventes>", "<envolventes xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">");
            //System.out.println(result);
            return result;

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String filterToString() {
        String start = "com os filtros ";
        StringBuilder result = new StringBuilder(start);

        if (distrito != null) {
            result.append("{distrito = ").append(distrito).append("}");
        }

        if (latitudeMin != null && latitudeMax != null && longitudeMin != null && longitudeMax != null) {
            result.append("{latitudeMin = ").append(latitudeMin).append("; latitudeMax = ").append(latitudeMax).append("; longitudeMin = ").append(longitudeMin).append(";longitudeMax = ").append(longitudeMax).append("}");
        }

        return result.toString().equals(start) ? "" : result.toString();
    }
}
