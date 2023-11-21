package DAO;

import Interface.DAO;
import Models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class UserDAO implements DAO {
    @Override
    public void save(Object user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Object user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Object user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public User findByID(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    @Override
    public List findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Object> users = (List<Object>) session.createQuery("From User").list();
        session.close();
        return users;
    }

    public User findByLoginAndPassword(String username, String password) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<User> users = (List<User>) session.createQuery("From User where username = " + "'" + username + "' and password = '" + password + "'").list();
        if(!users.isEmpty()){
            session.close();
            return users.get(0);
        }else{
            User user = new User();
            return user;
        }
    }

//    @Override
//    public List findBySmth(String username, String password) {
//        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
//        List<Object> users = (List<Object>) session.createQuery("Select From User where username = " + "'" + username + "' and password = '" + password + "'").list();
//        session.close();
//        return users;
//    }
}
