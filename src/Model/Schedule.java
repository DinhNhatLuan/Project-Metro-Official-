package Model;

import java.sql.Date;
import java.sql.Time;

public class Schedule {
    private String scheduleID;
    private java.sql.Date departureDate;
    private java.sql.Time departureTime;
    private String status;
    private String trainID;
    private String employeeID;
    private int reservedTicketCount;

    public Schedule() {
        super()
    }

    public Schedule(String scheduleID, Date departureDate, Time departureTime,
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

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
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
