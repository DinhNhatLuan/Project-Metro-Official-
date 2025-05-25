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
public class TicketType {
    private String ticketTypeID;
    private String description;
    private int basePrice;


    public TicketType() {
        super();
    }

    public TicketType(String ticketTypeID, String description, int basePrice) {
        this.ticketTypeID = ticketTypeID;
        this.description = description;
        this.basePrice = basePrice;
    }

    public String getTicketTypeID() {
        return ticketTypeID;
    }

    public void setTicketTypeID(String ticketTypeID) {
        this.ticketTypeID = ticketTypeID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

}
