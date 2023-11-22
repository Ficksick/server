package Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int film_id;
    @Column(name = "title", nullable = false, length = 256)
    private String title;
    @Column(name = "genre", nullable = false, length = 256)
    private String genre;
    @Column(name = "director", nullable = false, length = 256)
    private String director;
    @Column(name = "mainActor", nullable = false, length = 256)
    private String mainActor;
    @Column(name = "duration", nullable = false)
    private Time duration;
    @Column(name = "age", nullable = false)
    private int age;
    @OneToMany(mappedBy = "screenings", fetch = FetchType.EAGER)
    private List<Screening> screenings;

    private Film(int id, String title, String genre, String director, String mainActor, Time duration, int age) {
        this.film_id = id;
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.mainActor = mainActor;
        this.duration = duration;
        this.age = age;
    }

    private Film(){}

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

    public int getAge() {
        return age;
    }

    public Time getDuration() {
        return duration;
    }

    public void setAge(int age) {
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
