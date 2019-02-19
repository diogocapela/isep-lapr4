package utils;

import javafx.util.Pair;
import models.Cobertura;
import models.avaliacao.Caso;
import models.avaliacao.EstadoCaso;
import models.avaliacao.ObjetoSeguro;
import models.avaliacao.Validacao;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import services.CoberturaService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

public class XMLImporter {

    private CoberturaService coberturaService = new CoberturaService();

    public Caso importCasoFromString(String str) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(str)));

            doc.getDocumentElement().normalize();

            List<ObjetoSeguro> objetoSeguroList = new LinkedList<>();
            NodeList nodeListObjetosSegurados = doc.getElementsByTagName(XMLSettings.CASO_OBJECTOS_SEGURADOS).item(0).getChildNodes();

            for (int i = 0; i < nodeListObjetosSegurados.getLength(); i++) {
                Node nodeObjetoSeguro = nodeListObjetosSegurados.item(i);

                if (nodeObjetoSeguro.getNodeType() == Node.ELEMENT_NODE) {
                    Element osElement = (Element) nodeObjetoSeguro;
                    // Descrição do Objecto Seguro
                    String descricao = osElement.getElementsByTagName(XMLSettings.OBJECTO_SEGURO_DESCRICAO).item(0).getTextContent();
                    // Latitude do Objecto Seguro
                    Double latitude = Double.parseDouble(osElement.getElementsByTagName(XMLSettings.OBJECTO_SEGURO_LATITUDE).item(0).getTextContent());
                    // Longitude do Objecto Seguro
                    Double longitude = Double.parseDouble(osElement.getElementsByTagName(XMLSettings.OBJECTO_SEGURO_LONGITUDE).item(0).getTextContent());

                    List<Cobertura> coberturaList = new LinkedList<>();
                    NodeList nodeListCoberturas = osElement.getElementsByTagName(XMLSettings.OBJECTO_SEGURO_COBERTURAS).item(0).getChildNodes();
                    for (int j = 0; j < nodeListCoberturas.getLength(); j++) {
                        Node nodeCobertura = nodeListCoberturas.item(j);
                        if (nodeCobertura.getNodeType() == Node.ELEMENT_NODE) {
                            Element cobElement = (Element) nodeCobertura;
                            // Titulo da Cobertura
                            String titulo_cobertura = cobElement.getElementsByTagName(XMLSettings.COBERTURA_TITULO).item(0).getTextContent();
                            // Descrição da Cobertura
                            String descricao_cobertura = cobElement.getElementsByTagName(XMLSettings.COBERTURA_DESCRICAO).item(0).getTextContent();

                            coberturaList.add(coberturaService.createCobertura(titulo_cobertura, descricao_cobertura));
                        }
                    }

                    objetoSeguroList.add(new ObjetoSeguro(descricao, latitude, longitude, coberturaList));
                }
            }

            Validacao validacao = null;
            NodeList nodeListValidacao = doc.getElementsByTagName(XMLSettings.VALIDACAO);
            if (nodeListValidacao.getLength() > 0) {
                Node nodeValidacao = nodeListValidacao.item(0);
                if (nodeValidacao.getNodeType() == Node.ELEMENT_NODE) {
                    Element valElement = (Element) nodeValidacao;
                    if (valElement.getTextContent().equalsIgnoreCase("sim".toLowerCase())) {
                        validacao = new Validacao();
                    }
                }
            }

            if (validacao == null) {
                return new Caso(objetoSeguroList, EstadoCaso.ESPERA);
            } else {
                return new Caso(objetoSeguroList, EstadoCaso.ESPERA, validacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObjetoSeguro importObjectoSeguroFromString(String str) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(str)));

            doc.getDocumentElement().normalize();

            ObjetoSeguro objetoSeguro = null;
            NodeList nodeListComparacao = doc.getElementsByTagName(XMLSettings.COMPARACAO);
            Node nodeObjetoSeguro = nodeListComparacao.item(0);

            if (nodeObjetoSeguro.getNodeType() == Node.ELEMENT_NODE) {
                Element osElement = (Element) nodeObjetoSeguro;
                // Descrição do Objecto Seguro
                String descricao = osElement.getElementsByTagName(XMLSettings.OBJECTO_SEGURO_DESCRICAO).item(0).getTextContent();
                // Latitude do Objecto Seguro
                Double latitude = Double.parseDouble(osElement.getElementsByTagName(XMLSettings.OBJECTO_SEGURO_LATITUDE).item(0).getTextContent());
                // Longitude do Objecto Seguro
                Double longitude = Double.parseDouble(osElement.getElementsByTagName(XMLSettings.OBJECTO_SEGURO_LONGITUDE).item(0).getTextContent());

                List<Cobertura> coberturaList = new LinkedList<>();
                NodeList nodeListCoberturas = osElement.getElementsByTagName(XMLSettings.OBJECTO_SEGURO_COBERTURAS).item(0).getChildNodes();
                for (int j = 0; j < nodeListCoberturas.getLength(); j++) {
                    Node nodeCobertura = nodeListCoberturas.item(j);
                    if (nodeCobertura.getNodeType() == Node.ELEMENT_NODE) {
                        Element cobElement = (Element) nodeCobertura;
                        // Titulo da Cobertura
                        String titulo_cobertura = cobElement.getElementsByTagName(XMLSettings.COBERTURA_TITULO).item(0).getTextContent();
                        // Descrição da Cobertura
                        String descricao_cobertura = cobElement.getElementsByTagName(XMLSettings.COBERTURA_DESCRICAO).item(0).getTextContent();

                        coberturaList.add(coberturaService.createCobertura(titulo_cobertura, descricao_cobertura));
                    }
                }

                objetoSeguro = new ObjetoSeguro(descricao, latitude, longitude, coberturaList);
            }

            return objetoSeguro;
        } catch (Exception e) {
            e.getCause();
        }
        return null;
    }

    public Pair<Long, Long> importIdMatrizesFromString(String str) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(str)));

            doc.getDocumentElement().normalize();

            Long idMatrizA = null;
            Long idMatrizB = null;
            NodeList nodeListComparacao = doc.getElementsByTagName(XMLSettings.COMPARACAO);
            Node nodeComparacao = nodeListComparacao.item(0);

            Element comparacaoElem = (Element) nodeComparacao;
            NodeList nodeMatrizesList = comparacaoElem.getElementsByTagName("matrizes");
            Node matrizes = nodeMatrizesList.item(0);

            Node matrizA = matrizes.getFirstChild();
            Node matrizB = matrizes.getLastChild();

            // Matriz A
            Element matrizAElement = (Element) matrizA;
            idMatrizA = Long.parseLong(matrizAElement.getTextContent());
            // Matriz B
            Element matrizBElement = (Element) matrizB;
            idMatrizB = Long.parseLong(matrizBElement.getTextContent());

            return new Pair<>(idMatrizA, idMatrizB);

        } catch (Exception e) {
            e.getCause();
        }
        return null;
    }
}
