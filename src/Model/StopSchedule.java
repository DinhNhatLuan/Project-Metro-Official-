package Model;

import java.time.LocalTime;

public class StopSchedule {
    private int scheduleId;
    private int stationId;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private int orderNumber;

    public StopSchedule() {}

    public StopSchedule(int scheduleId, int stationId, LocalTime arrivalTime, LocalTime departureTime, int orderNumber) {
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

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}