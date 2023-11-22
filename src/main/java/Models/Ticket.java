package Models;

import javax.persistence.*;

public class Ticket {
    private int id;

    @OneToOne
    //private int screening_id;

    private int seatNUumber;

    private double price;

    private String filmTitle;

    private String hallName;
}
