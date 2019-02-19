package raconsole.controller;

import controllers.AddEnvolventeController;
import georef.GeoRef;
import georef.GeoRefFactory;
import georef.models.Location;

public class AR06Controller {

    private final AddEnvolventeController addEnvolventeController = new AddEnvolventeController();
    private final GeoRef geoRef = GeoRefFactory.createGeoRef();
    private final GeoRef geoRefGoogle = GeoRefFactory.createGeoRefGoogle();
    private final GeoRef geoRefBing = GeoRefFactory.createGeoRefBing();

    public Location getLocationFromPostalCode(String postalCode) {
        return geoRef.getLocationFromPostalCode(postalCode);
    }

    public String downloadImageOfAddress(String address) {
        return geoRef.downloadImageOfAddress(address);
    }

    public String downloadImageOfAddressGoogle(String address) {
        return geoRefGoogle.downloadImageOfAddress(address);
    }

    public String downloadImageOfAddressBing(String address) {
        return geoRefBing.downloadImageOfAddress(address);
    }

    public void addEnvolvente(String titulo, String descricao, Double latitude, Double longitude) {
        addEnvolventeController.addEnvolvente(titulo, descricao, latitude, longitude);
    }

}
