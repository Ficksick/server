package DAO;

import Interface.DAO;
import Models.Hall;
import Models.Ticket;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class HallDAO implements DAO {
    @Override
    public void save(Object hall) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(hall);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Object hall) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(hall);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Object hall) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(hall);
        transaction.commit();
        session.close();
    }

    @Override
    public Hall findByID(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Hall hall = session.get(Hall.class, id);
        session.close();
        return hall;
    }

    @Override
    public List findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Object> halls = (List<Object>) session.createQuery("From Hall").list();
        session.close();
        return halls;
    }
}
