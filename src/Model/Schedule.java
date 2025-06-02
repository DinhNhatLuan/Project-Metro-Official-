
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**

package Model;

/**
 *
 * @author Acer
 */
package Model;

import java.time.LocalTime;
import java.time.LocalDate;

public class Schedule {
    private int scheduleID;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private String status;
    private int trainID;
    private int driverID;
    private int scMakerID;
    private int reservedTicketCount;
    private int routeId;

    public Schedule() {
        super();
    }

    public Schedule(int scheduleID, LocalDate departureDate, LocalTime departureTime,
                    String status, int trainID, int driverID, int scMakerID,
                    int reservedTicketCount, int routeId) {
        this.scheduleID = scheduleID;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.status = status;
        this.trainID = trainID;
        this.driverID = driverID;
        this.scMakerID = scMakerID;
        this.reservedTicketCount = reservedTicketCount;
        this.routeId = routeId;
    }

    // Getters and Setters

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTrainID() {
        return trainID;
    }

    public void setTrainID(int trainID) {
        this.trainID = trainID;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public int getScMakerID() {
        return scMakerID;
    }

    public void setScMakerID(int scMakerID) {
        this.scMakerID = scMakerID;
    }

    public int getReservedTicketCount() {
        return reservedTicketCount;
    }

    public void setReservedTicketCount(int reservedTicketCount) {
        this.reservedTicketCount = reservedTicketCount;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}