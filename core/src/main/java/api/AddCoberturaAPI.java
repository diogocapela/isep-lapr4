/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import services.CoberturaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author pushdword
 */
@WebServlet("/addCobertura")
public class AddCoberturaAPI extends HttpServlet {

    private final CoberturaService cs = new CoberturaService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String titulo = request.getParameter("titulo");
        String descricao = request.getParameter("descricao");

        if (cs.createCobertura(titulo, descricao) != null) {
            response.setStatus(200 /*ok*/);
        } else {
            response.setStatus(403 /*forbidden*/);
        }

    }
}
