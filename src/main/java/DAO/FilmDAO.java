package DAO;

import Interface.DAO;
import Models.Film;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class FilmDAO implements DAO {
    @Override
    public void save(Object film) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(film);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Object film) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(film);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Object film) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(film);
        transaction.commit();
        session.close();
    }

    @Override
    public Film findByID(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Film film = session.get(Film.class, id);
        session.close();
        return film;
    }

    @Override
    public List findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Object> films = (List<Object>) session.createQuery("From Film").list();
        session.close();
        return films;
    }
}
