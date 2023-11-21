package Interface;

import Models.User;

import java.util.List;

public interface DAO<T> {
    void save(T obj);

    void delete(T obj);

    void update(T obj);

    T findByID(int id);

    List<T> findAll();

   // List<User> findBySmth(String username, String password);

    //List<T> findBySmth(String obj, String obj1);
}
