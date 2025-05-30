package Model;

public class Route {
    private int routeId;
    private String routeName;
    private int length;

    public Route() {
        super();
    }

    public Route(int routeId, String routeName, int length) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.length = length;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
