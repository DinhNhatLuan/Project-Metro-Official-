package Model;

public class StationOrder {
    private int stationOrderId;
    private int departureStationId;
    private int arrivalStationId;
    private int extraPrice;

    public StationOrder() {}

    public StationOrder(int stationOrderId, int departureStationId, int arrivalStationId, int extraPrice) {
        this.stationOrderId = stationOrderId;
        this.departureStationId = departureStationId;
        this.arrivalStationId = arrivalStationId;
        this.extraPrice = extraPrice;
    }

    public int getStationOrderId() { return stationOrderId; }
    public void setStationOrderId(int stationOrderId) { this.stationOrderId = stationOrderId; }
    public int getDepartureStationId() { return departureStationId; }
    public void setDepartureStationId(int departureStationId) { this.departureStationId = departureStationId; }
    public int getArrivalStationId() { return arrivalStationId; }
    public void setArrivalStationId(int arrivalStationId) { this.arrivalStationId = arrivalStationId; }
    public int getExtraPrice() { return extraPrice; }
    public void setExtraPrice(int extraPrice) { this.extraPrice = extraPrice; }
}
