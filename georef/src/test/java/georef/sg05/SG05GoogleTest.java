package georef.sg05;

import georef.GeoRef;
import georef.models.Route;
import georef.strategies.GoogleGeoRefStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SG05GoogleTest {

    private GeoRef geoRef;

    @Before
    public void setUp() {
        geoRef = new GeoRef(new GoogleGeoRefStrategy());
    }

    @Test(expected = IllegalArgumentException.class)
    public void garantirQueLancaExcepcaoQuandoUmDosEnderecosNuloOuVazio() {
        Route route = geoRef.getRouteBetweenLocations(null, "Rua da Boavista,27, Porto", "driving");
    }

    @Test(expected = IllegalArgumentException.class)
    public void garantirQueLancaExcepcaoQuandoUmaDasCoordenadasNula() {
        Route route = geoRef.getRouteBetweenLocations(null, -8.614949, 41.155571, -8.613799, "driving");
    }

    @Test
    public void garantirQueCalculaRouteComLocomocaoNula() {
        Route route = geoRef.getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", null);
        Assert.assertTrue("A Route não deveria ser null.", route != null);
    }

    @Test
    public void garantirQueCalculaRouteSemDefinirLocomocao() {
        Route route = geoRef.getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", "");
        Assert.assertTrue("A Route não deveria ser null.", route != null);
    }

    @Test
    public void garantirQueCalculaRouteDrivingDeafultComLocomocaoDesconhecida() {
        Route route = geoRef.getRouteBetweenLocations("Travessa de Cedofeita, 72, Porto", "Rua da Boavista,27, Porto", "avião");
        Assert.assertTrue("A Route não deveria ser null.", route != null);
    }

    @Test
    public void garantirQueRetornaNullCasoHajaFalhasNoServico() {
        Route route = geoRef.getRouteBetweenLocations("000000000000000000", "Rua", "driving");
        Assert.assertTrue("A route sai sempre a null caso algo falhe!", route == null);
    }


}
