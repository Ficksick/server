package Service;

import DAO.UserDAO;
import Models.User;

import java.util.List;

public class UserService {
    UserDAO userDAO = new UserDAO();

    public UserService() {
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User findUser(int id) {
        User user = (User) userDAO.findByID(id);
        return user;
    }

    public void saveUser(User user) {
        userDAO.save(user);
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public User findByUsernameAndPassword(String username, String password){
        User user = userDAO.findByLoginAndPassword(username, password);
        return user;
    }
}
