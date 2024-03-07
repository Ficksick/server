package Service;

import DAO.TicketDAO;
import Models.Screening;
import Models.Ticket;

import java.util.List;

public class TicketService {
    TicketDAO ticketDAO = new TicketDAO();

    public TicketService() {
    }

    public List<Ticket> findAll() {
        return ticketDAO.findAll();
    }

    public void saveTicket(Ticket ticket) {
        ticketDAO.save(ticket);
    }

    public void updateTicket(Ticket ticket) {
        ticketDAO.update(ticket);
    }

    public void deleteTicket(Ticket ticket) {
        ticketDAO.delete(ticket);
    }

    public Ticket findByID(int id) {
        return ticketDAO.findByID(id);
    }
}
