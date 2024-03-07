package Models;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "tickets")

public class Ticket implements Serializable {
    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "screening_id")
    private Screening screening;
    @Column(name = "seat_number")
    private int seatNumber;
    @Column(name = "price")
    private double price;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private static final long serialVersionUID = 678912345L;

    public User getUser() {return this.user;}
    public double getPrice() {return price;}
    public int getSeatNumber() {return seatNumber;}
    public Screening getScreening() {return this.screening;}
    public int getId() {return id;}
    public void setPrice(double price) {
        this.price = price;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSeatNumber(int numb) {
        this.seatNumber = numb;
    }

    public String toString(){
        return "id = " + id +
                "\nscreening = " + screening +
                "\nseatNumber = " + seatNumber +
                "\nprice = " + price +
                "\nuser = " + user;
    }
}
