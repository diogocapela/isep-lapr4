/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.matriz.Matriz;
import repository.MatrizRepository;

import java.io.Serializable;
import java.util.List;

/**
 * @author pushdword
 */
public class MatrizBaseService implements Serializable {
    private final MatrizRepository mr = new MatrizRepository();

    public MatrizBaseService() {
        /**/
    }


    public boolean existsOnDbById(Long id) {
        return mr.findById(id) != null;
    }

    public Matriz getMatrizById(Long id) {
        return mr.findById(id);
    }

    public List<String[]> getMatrizDTOById(Long id) {
        return getMatrizById(id).toExporterDTO();
    }

}
