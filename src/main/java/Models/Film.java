package Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "film")
public class Film implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int film_id;
    @Column(name = "title")
    private String title;
    @Column(name = "genre")
    private String genre;
    @Column(name = "director")
    private String director;
    @Column(name = "mainActor")
    private String mainActor;
    @Column(name = "duration")
    private Time duration;
    @Column(name = "age")
    private String age;
    @OneToMany(mappedBy = "film", fetch = FetchType.EAGER)
    private List<Screening> screenings;
    private static final long serialVersionUID = 456789123L;
    private Film(int id, String title, String genre, String director, String mainActor, Time duration, String age) {
        this.film_id = id;
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.mainActor = mainActor;
        this.duration = duration;
        this.age = age;
    }

    public Film(){}

    public int getFilm_id() {
        return film_id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getMainActor() {
        return mainActor;
    }

    public String getAge() {
        return age;
    }

    public Time getDuration() {
        return duration;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setMainActor(String mainActor) {
        this.mainActor = mainActor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "model.Film" +
                "\nid = " + film_id +
                "\ntitle = " + title +
                "\ngenre = " + genre +
                "\ndirector = " + director +
                "\nmain actor = " + mainActor +
                "\nduration = " + duration +
                "\nage = " + age;
    }
}
