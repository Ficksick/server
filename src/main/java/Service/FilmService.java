package Service;

import DAO.FilmDAO;
import DAO.HallDAO;
import Models.Film;

import java.util.List;

public class FilmService {
    FilmDAO filmDAO = new FilmDAO();

    public FilmService() {

    }

    public List<Film> findAll() {
        return filmDAO.findAll();
    }

    public Film findFilm(int id) {
        Film film = (Film) filmDAO.findByID(id);
        return film;
    }

    public void saveFilm(Film film) {
        filmDAO.save(film);
    }

    public void deleteFilm(Film film) {
        filmDAO.delete(film);
    }

    public void updateFilm(Film film) {
        filmDAO.update(film);
    }
}
