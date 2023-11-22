package Service;

import DAO.ScreeningDAO;
import Models.Screening;

import java.util.List;

public class ScreeningService {
    ScreeningDAO screeningDAO = new ScreeningDAO();

    public ScreeningService() {
    }

    public List<Screening> findAll() {
        return screeningDAO.findAll();
    }

    public void saveScreening(Screening screening) {
        screeningDAO.save(screening);
    }
    public void updateScreening(Screening screening) {
        screeningDAO.update(screening);
    }
    public void deleteScreening(Screening screening) {
        screeningDAO.delete(screening);
    }
}
