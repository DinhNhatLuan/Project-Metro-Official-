package Model;

import java.time.LocalDateTime;

public class StopSchedule {
    private String scheduleId;
    private String stationId;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private int orderNumber;

    public StopSchedule() {}

    public StopSchedule(String scheduleId, String stationId, LocalDateTime arrivalTime, LocalDateTime departureTime, int orderNumber) {
        this.scheduleId = scheduleId;
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.orderNumber = orderNumber;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
