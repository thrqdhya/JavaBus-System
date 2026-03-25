package repository;

import model.Ticket;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {
    private List<Ticket> ticketList = new ArrayList<>();

    public void add(Ticket ticket) {
        ticketList.add(ticket);
    }

    public void remove(int id) {
        ticketList.removeIf(t -> t.getId() == id);
    }

    public Ticket findById(int id) {
        for (Ticket t : ticketList) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public List<Ticket> getAll() {
        return ticketList;
    }
}