package Models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hall")
public class Hall implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hall_id;
    @Column(name = "hallName")
    private String hallName;
    @Column(name = "capacity")
    private int capacity;
    private static final long serialVersionUID = 234567891L;

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

    public String toString(){
        return "id = " + hall_id +
                "\nhallName = " + hallName +
                "\ncapacity = " + capacity;
    }
}
