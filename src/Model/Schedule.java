
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
    private String scheduleID;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private String status;
    private String trainID;
    private String employeeID;
    private int reservedTicketCount;

    public Schedule() {
        super();
    }

    public Schedule(String scheduleID, LocalDate departureDate, LocalTime departureTime,
                    String status, String trainID, String employeeID, int reservedTicketCount) {
        this.scheduleID = scheduleID;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.status = status;
        this.trainID = trainID;
        this.employeeID = employeeID;
        this.reservedTicketCount = reservedTicketCount;
    }

    // Getters and Setters

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
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

    public String getTrainID() {
        return trainID;
    }

    public void setTrainID(String trainID) {
        this.trainID = trainID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public int getReservedTicketCount() {
        return reservedTicketCount;
    }

    public void setReservedTicketCount(int reservedTicketCount) {
        this.reservedTicketCount = reservedTicketCount;
    }
}
