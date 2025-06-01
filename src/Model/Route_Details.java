package Model;

public class Route_Details {
    private int routeId;
    private int stationId;
    private int stationOrder;

    public Route_Details() {
        super();
    }

    public Route_Details(int routeId, int stationId, int stationOrderId) {
        this.routeId = routeId;
        this.stationId = stationId;
        this.stationOrder = stationOrderId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getStationOrder() {
        return stationOrder;
    }

    public void setStationOrder(int stationOrderId) {
        this.stationOrder = stationOrderId;
    }
}
