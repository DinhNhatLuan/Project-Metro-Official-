package Model;

public class Station {
    private int stationId;
    private String name;
    private String location;

    public Station() {}

    public Station(int stationId, String name, String location) {
        this.stationId = stationId;
        this.name = name;
        this.location = location;
    }

    public int getStationId() { return stationId; }
    public void setStationId(int stationId) { this.stationId = stationId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
