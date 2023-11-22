package DAO;

import Interface.DAO;
import Models.Screening;
import Models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class ScreeningDAO implements DAO {

    @Override
    public void save(Object screening) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(screening);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Object screening) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(screening);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Object screening) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(screening);
        transaction.commit();
        session.close();
    }

    @Override
    public Screening findByID(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Screening screening = session.get(Screening.class, id);
        session.close();
        return screening;
    }

    @Override
    public List findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Object> screenings = (List<Object>) session.createQuery("From Screenings").list();
        session.close();
        return screenings;
    }
}
