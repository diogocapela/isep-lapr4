package georef.models;

public class Route {

    private Location locationA;
    private Location locationB;
    private Double distance;
    private Double time;
    private String locomocao;

    // Constructors
    //==================================================================

    public Route() {
    }

    public Route(Location locationA, Location locationB, Double distance, Double time, String locomocao) {
        this.locationA = locationA;
        this.locationB = locationB;
        this.distance = distance;
        this.time = time;
        this.locomocao = locomocao;
    }

    // Getters
    //==================================================================

    public Location getLocationA() {
        return locationA;
    }

    public Location getLocationB() {
        return locationB;
    }

    public Double getDistance() {
        return distance;
    }

    public Double getTime() {
        return time;
    }

    public Integer getIntTime() {
        return Math.toIntExact((long) (time / 60));
    }

    public String getLocomocao() {
        return locomocao;
    }

    // Methods
    //==================================================================

    @Override
    public String toString() {
        return "Route{" +
            "locationA=" + locationA +
            ", locationB=" + locationB +
            ", distance=" + distance + "km" +
            ", time=" + getIntTime() + "m" +
            '}';
    }

}
