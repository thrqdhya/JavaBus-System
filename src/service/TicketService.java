package service;

import java.util.List;
import model.Ticket;
import repository.TicketRepository;

public class TicketService {

    private TicketRepository ticketRepository;

    // Constructor
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // 🎯 1. Tiket oluştur (Create Ticket)
    public void createTicket(Ticket ticket) {
        if (isSeatAvailable(ticket.getKoltukNo())) {
            ticketRepository.add(ticket);
            System.out.println("Bilet basarıyla olusturuldu!");
        } else {
            System.out.println("Bu koltuk dolu!");
        }
    }

    // 🎯 2. Tüm biletleri listele (Get All Tickets)
    public void getAllTickets() {
        List<Ticket> tickets = ticketRepository.getAll();

        if (tickets.isEmpty()) {
            System.out.println("Hiç bilet bulunamadı.");
        } else {
            for (Ticket ticket : tickets) {
                System.out.println(ticket);
            }
        }
    }

    // 🎯 3. Bilet iptal et (Cancel Ticket)
    public void cancelTicket(int id) {
        Ticket ticket = ticketRepository.findById(id);

        if (ticket != null) {
            ticketRepository.remove(id);
            System.out.println("Bilet iptal edildi.");
        } else {
            System.out.println("Bilet bulunamadı!");
        }
    }

    // 🎯 4. ID ile bilet bul
    public Ticket findTicketById(int id) {
        return ticketRepository.findById(id);
    }

    // 🎯 5. Koltuk kontrolü (Seat availability)
    public boolean isSeatAvailable(int seatNo) {
        List<Ticket> tickets = ticketRepository.getAll();

        for (Ticket ticket : tickets) {
            if (ticket.getKoltukNo() == seatNo) {
                return false;
            }
        }
        return true;
    }
}

