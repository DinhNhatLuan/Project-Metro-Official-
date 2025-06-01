package Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**

/**
 *
 * @author Acer
 */
public class TicketType {
    private int ticketTypeID;
    private String description;
    private int basePrice;


    public TicketType() {
        super();
    }

    public TicketType(int ticketTypeID, String description, int basePrice) {
        this.ticketTypeID = ticketTypeID;
        this.description = description;
        this.basePrice = basePrice;
    }

    public int getTicketTypeID() {
        return ticketTypeID;
    }

    public void setTicketTypeID(int ticketTypeID) {
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
