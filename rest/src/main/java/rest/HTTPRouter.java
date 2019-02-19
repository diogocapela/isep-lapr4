package rest;

import rest.routes.*;
import rest.utils.FormatUtils;

import java.io.DataOutputStream;
import java.net.Socket;

public class HTTPRouter extends Thread {

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private HTTPServer housingServer;
    private HTTPMessage request;
    private HTTPMessage response;
    private FormatUtils formatUtils = new FormatUtils();
    private String outputFormat;

    public HTTPRouter(HTTPServer server, Socket socket, HTTPMessage request) {
        this.housingServer = server;
        this.socket = socket;
        this.request = request;
    }

    public void run() {
        try {
            String method = request.getMethod();
            String endpoint = request.getURI();
            String parameters = request.getParameters();
            outputFormat = formatUtils.defineOutputFormat(parameters);
            response = new HTTPMessage();
            dataOutputStream = new DataOutputStream(socket.getOutputStream());


            if (housingServer.acceptRequest()) {
                handleMethod(method, endpoint);
                housingServer.terminateRequest();
            } else {
                response.setResponseStatus(Settings.STATUS_NOT_ALLOWED);
                response.setContentFromString(formatUtils.buildOutputString("ERROR request limit reached", null), Settings.xmlOutputFormat);
            }

            response.send(dataOutputStream);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleMethod(String method, String endpoint) {
        switch (method) {
            // GET Router
            //========================================================
            case "GET":
                handleGETRequest(endpoint);
                break;

            // POST Router
            //========================================================
            case "POST":
                handlePOSTRequest(endpoint);
                break;

            // PUT Router
            //========================================================
            /*case "PUT":
                break;
            // DELETE Router
            //========================================================
            case "DELETE":
                break;*/

            default:
                response.setResponseStatus(Settings.STATUS_NOT_ALLOWED);
                response.setContentFromString(formatUtils.buildOutputString(Settings.STATUS_NOT_ALLOWED, null), Settings.xmlOutputFormat);
        }
    }

    private void handleGETRequest(String endpoint) {
        try {
            if (endpoint.startsWith("/obter-pedido")) {
                // SE02
                new SE02Route(request, response).resultadoAvaliacaoDeRisco();

            } else if (endpoint.equalsIgnoreCase("/status-processamento")) {
                // SE06
                new SE06Route(housingServer, request, response).conhecerDisponibilidadeServicoAvaliacaoRisco();
            } else if (endpoint.equalsIgnoreCase("/statistics-report")) {
                // SE07
                new SE07Route(housingServer, request, response).relatorioestatisticodadisponibilidade();
            } else if (endpoint.equalsIgnoreCase("/login")) {
                new SEAuthRoute(housingServer, request, response);
                response.setResponseStatus("403");
            } else {
                response.setResponseStatus(Settings.STATUS_NOT_FOUND);
                response.setContentFromString(formatUtils.buildOutputString(Settings.STATUS_NOT_FOUND, null), Settings.xmlOutputFormat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePOSTRequest(String endpoint) {
        try {
            switch (endpoint) {
                case "/submeter-pedido":
                    // SE01
                    new SE01Route(request, response).submeterPedidoAvaliacao();
                    break;
                case "/comparar-avaliacao-objecto":
                    // SE04
                    new SE04Route(request, response).compararAvaliacaoObjecto();
                    break;
                case "/submeter-conjunto-pedidos":
                    // SE05
                    new SE05Route(request, response).submeterConjuntoPedidoAvaliacao();
                    break;
                case "/resultado-pedido":
                    // SE03
                    new SE03Route(request, response).resultadoAvaliacaoDeRiscoConcluidos();
                    break;
                default:
                    response.setResponseStatus(Settings.STATUS_NOT_FOUND);
                    response.setContentFromString(formatUtils.buildOutputString(Settings.STATUS_NOT_FOUND, null), Settings.xmlOutputFormat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

