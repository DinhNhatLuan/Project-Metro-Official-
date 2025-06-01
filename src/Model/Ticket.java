package Model;

import java.time.LocalDateTime;

public class Ticket {
    private int ticketId;
    private int price;
    private int ticketTypeId;
    private LocalDateTime purchaseTime;
    private int scheduleId;
    private int stationOrderId;

    public Ticket() {}

    public Ticket(int ticketId, int price, int ticketTypeId, LocalDateTime purchaseTime, int scheduleId, int stationOrderId) {
        this.ticketId = ticketId;
        this.price = price;
        this.ticketTypeId = ticketTypeId;
        this.purchaseTime = purchaseTime;
        this.scheduleId = scheduleId;
        this.stationOrderId = stationOrderId;
    }

    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getTicketTypeId() { return ticketTypeId; }
    public void setTicketTypeId(int ticketTypeId) { this.ticketTypeId = ticketTypeId; }
    public LocalDateTime getPurchaseTime() { return purchaseTime; }
    public void setPurchaseTime(LocalDateTime purchaseTime) { this.purchaseTime = purchaseTime; }
    public int getScheduleId() { return scheduleId; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }
    public int getStationOrderId() { return stationOrderId; }
    public void setStationOrderId(int stationOrderId) { this.stationOrderId = stationOrderId; }
}
