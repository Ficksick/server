package Models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @Column(name = "screening_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int screening_id;

    @Column(name = "film_id")
    @ManyToOne
    //у одного билета может быть только один фильм
    //у одного фильма может быть много билетов
    private int film_id;

    @Column(name = "hall_id")
    @OneToOne
    private int hall_id;

    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;


}
