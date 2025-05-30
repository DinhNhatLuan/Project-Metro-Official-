package Model;

import java.time.LocalDateTime;

public class StopSchedule {
    private int scheduleId;
    private int stationId;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private int orderNumber;

    public StopSchedule() {}

    public StopSchedule(int scheduleId, int stationId, LocalDateTime arrivalTime, LocalDateTime departureTime, int orderNumber) {
        this.scheduleId = scheduleId;
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.orderNumber = orderNumber;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
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
