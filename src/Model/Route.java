package Model;

public class Route {
    private int routeId;
    private String routeName;
    private int length;
    private int extraCost; // Đổi tên thuộc tính thành extraCost (theo Java convention)

    public Route() {
        super();
    }

    public Route(int routeId, String routeName, int length, int extraCost) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.length = length;
        this.extraCost = extraCost;
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

    public int getExtraCost() {
        return extraCost;
    }

    public void setExtraCost(int extraCost) {
        this.extraCost = extraCost;
    }
}