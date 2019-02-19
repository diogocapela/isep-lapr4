package georef.models;

public class Location {

    private String title;
    private String street;
    private Integer doorNumber;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private String country;
    private String district;
    private String city;
    private String postalCode;

    public Location() {

    }

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Double altitude) {
        this.altitude = altitude;
    }

    // Getters
    //==================================================================

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(Integer doorNumber) {
        this.doorNumber = doorNumber;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    // Setters
    //==================================================================

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    // Methods
    //==================================================================

    @Override
    public String toString() {
        return "Location{" +
            "title='" + title + '\'' +
            ", street='" + street + '\'' +
            ", doorNumber=" + doorNumber +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", altitude=" + altitude +
            ", country='" + country + '\'' +
            ", district='" + district + '\'' +
            ", city='" + city + '\'' +
            ", postalCode='" + postalCode + '\'' +
            '}';
    }
}
