package Models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "screenings")
public class Screening implements Serializable {
    @Id
    @Column(name = "screening_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int screening_id;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
    @OneToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;
    @Column(name="date")
    private Date date;
    @Column(name = "start_time")
    private Time startTime;
    @Column(name = "end_time")
    private Time endTime;

    private static final long serialVersionUID = 345678912L;

    public Screening() {

    }

    public Screening(int id, Date date, Time start, Time end) {
        this.screening_id = id;
        this.date = date;
        this.startTime = start;
        this.endTime = end;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate(){
        return date;
    }
    public void setStartTime(Time start) {
        this.startTime = start;
    }

    public void setEndTime(Time end) {
        this.endTime = end;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Film getFilm() {
        return film;
    }

    public Hall getHall() {
        return hall;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public int getScreening_id(){
        return screening_id;
    }

    public String toString(){
        return "id = " + screening_id +
                "\nfilm = " + film +
                "\nhall = " + hall +
                "\ndate = " + date +
                "\nstart = " + startTime +
                "\nend = " + endTime;
    }
}
