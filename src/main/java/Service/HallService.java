package Service;

import DAO.HallDAO;
import Models.Hall;

import java.util.List;

public class HallService {
    HallDAO hallDAO = new HallDAO();

    public HallService(){

    }

    public List<Hall> findAll() {
        return hallDAO.findAll();
    }

    public Hall findHall(int id) {
        Hall hall = (Hall) hallDAO.findByID(id);
        return hall;
    }

    public void saveHall(Hall hall) {
        hallDAO.save(hall);
    }

    public void deleteHall(Hall hall) {
        hallDAO.delete(hall);
    }

    public void updateHall(Hall hall) {
        hallDAO.update(hall);
    }
}
