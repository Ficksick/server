package Models;

import javax.persistence.*;

@Entity
@Table(name = "hall")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hall_id;
    @Column(name = "hallName")
    private String hallName;
    @Column(name = "capacity")
    private int capacity;

    public Hall() {

    }

    public Hall(int id, String hallName, int capacity) {
        this.hall_id = id;
        this.hallName = hallName;
        this.capacity = capacity;
    }

    public int getHall_id() {
        return hall_id;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHall_id(int id) {
        this.hall_id = id;
    }

    public void setHallName(String name) {
        this.hallName = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
