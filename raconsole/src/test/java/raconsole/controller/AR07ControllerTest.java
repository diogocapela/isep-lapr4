package raconsole.controller;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AR07ControllerTest {

    private AR07Controller ar07ControllerWrongFilter = new AR07Controller(1D, 2D, null, null, null);
    private AR07Controller ar07ControllerNoFilter = new AR07Controller(1D, 2D, null, null, null);
    private AR07Controller ar07ControllerDistritoFilter = new AR07Controller(null, null, null, null, "porto");
    private AR07Controller ar07ControllerCoordenadasFilter = new AR07Controller(1D, 2D, 3D, 4D, null);
    private AR07Controller ar07ControllerAllFilter = new AR07Controller(1D, 2D, 3D, 4D, "porto");


    @Test
    public void testWrongFilterControllerTest() {
        System.out.println("controller que seja criado com filtros errados comporta-se como sem filtros");
        List<String> listaWrongFilter = ar07ControllerWrongFilter.listarEnvolventesRegistadas();
        List<String> listaNoFilter = ar07ControllerNoFilter.listarEnvolventesRegistadas();
        assertEquals(listaNoFilter, listaWrongFilter);
    }

    @Test
    public void testDistritoFilterControllerTest() {
        System.out.println("controller com filtro de distrito");
        List<String> listaNoFilter = ar07ControllerNoFilter.listarEnvolventesRegistadas();
        List<String> listaDistritoFilter = ar07ControllerDistritoFilter.listarEnvolventesRegistadas();
        assertTrue(listaNoFilter.size() >= listaDistritoFilter.size());
        boolean found = listaDistritoFilter.size() == 0;
        for (String srtDistrito : listaDistritoFilter) {
            for (String strNo : listaNoFilter) {
                found = found || strNo.equalsIgnoreCase(srtDistrito);
            }
            assertTrue(found);
        }
    }

    @Test
    public void testCoordenadasFilterControllerTest() {
        System.out.println("controller com filtro de coordenadas");
        List<String> listaNoFilter = ar07ControllerNoFilter.listarEnvolventesRegistadas();
        List<String> listaCoordenadasFilter = ar07ControllerCoordenadasFilter.listarEnvolventesRegistadas();
        assertTrue(listaNoFilter.size() >= listaCoordenadasFilter.size());
        boolean found = listaCoordenadasFilter.size() == 0;
        for (String strCoordenadas : listaCoordenadasFilter) {
            for (String strNo : listaNoFilter) {
                found = found || strNo.equalsIgnoreCase(strCoordenadas);
            }
            assertTrue(found);
        }
    }

    @Test
    public void testAllFilterControllerTest() {
        System.out.println("controller com filtros de distrito e de coordenadas");
        List<String> listaNoFilter = ar07ControllerNoFilter.listarEnvolventesRegistadas();
        List<String> listaCoordenadasFilter = ar07ControllerCoordenadasFilter.listarEnvolventesRegistadas();
        List<String> listaDistritoFilter = ar07ControllerDistritoFilter.listarEnvolventesRegistadas();
        List<String> listaAllFilter = ar07ControllerAllFilter.listarEnvolventesRegistadas();

        // no vs all
        assertTrue(listaNoFilter.size() >= listaAllFilter.size());
        boolean found = listaAllFilter.size() == 0;
        for (String strAll : listaAllFilter) {
            for (String strNo : listaNoFilter) {
                found = found || strNo.equalsIgnoreCase(strAll);
            }
            assertTrue(found);
        }

        // coordenadas vs all
        assertTrue(listaCoordenadasFilter.size() >= listaAllFilter.size());
        found = listaAllFilter.size() == 0;
        for (String strAll : listaAllFilter) {
            for (String strCoordenadas : listaCoordenadasFilter) {
                found = found || strCoordenadas.equalsIgnoreCase(strAll);
            }
            assertTrue(found);
        }

        // distrito vs all
        assertTrue(listaDistritoFilter.size() >= listaAllFilter.size());
        found = listaAllFilter.size() == 0;
        for (String strAll : listaAllFilter) {
            for (String strDistrito : listaDistritoFilter) {
                found = found || strDistrito.equalsIgnoreCase(strAll);
            }
            assertTrue(found);
        }
    }


}
