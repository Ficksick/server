package Service;

import DAO.TicketDAO;
import Models.Screening;

import java.util.List;

public class TicketService {
    TicketDAO ticketDAO = new TicketDAO();

    public TicketService() {
    }

    public List<Screening> findAll() {
        return ticketDAO.findAll();
    }

    public void saveScreening(Screening screening) {
        ticketDAO.save(screening);
    }
    public void updateScreening(Screening screening) {
        ticketDAO.update(screening);
    }
    public void deleteScreening(Screening screening) {
        ticketDAO.delete(screening);
    }
}
