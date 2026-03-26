package service;

import model.Ticket;
import model.Bus;
import model.Passenger;
import repository.TicketRepository;
import repository.BusRepository;
import repository.PassengerRepository;

import java.util.List;

public class TicketService {

    private TicketRepository ticketRepo;
    private BusRepository busRepo;
    private PassengerRepository passengerRepo;

    // Constructor
    public TicketService(TicketRepository ticketRepo, BusRepository busRepo, PassengerRepository passengerRepo) {
        this.ticketRepo = ticketRepo;
        this.busRepo = busRepo;
        this.passengerRepo = passengerRepo;
    }

    // 🎯 1. CREATE / BUY TICKET
    public void buyTicket(int ticketId, int busId, int passengerId, String seat) {
        Bus bus = busRepo.findById(busId);
        Passenger passenger = passengerRepo.findById(passengerId);

        if (bus == null) {
            System.out.println("Otobüs bulunamadı!");
            return;
        }
        if (passenger == null) {
            System.out.println("Yolcu bulunamadı!");
            return;
        }

        // cek seat sudah di-book
        for (Ticket t : ticketRepo.getAll()) {
            if (t.getBusId() == busId && t.getSeat().equalsIgnoreCase(seat)) {
                System.out.println("Bu koltuk dolu!");
                return;
            }
        }

        Ticket ticket = new Ticket(ticketId, busId, passengerId, seat);
        ticketRepo.add(ticket);
        System.out.println("Bilet basarıyla olusturuldu: " + ticket);
    }

    // 🎯 2. READ / Get All Tickets
    public List<Ticket> getAllTickets() {
        return ticketRepo.getAll();
    }

    // Optional: print all tickets
    public void printAllTickets() {
        List<Ticket> tickets = ticketRepo.getAll();
        if (tickets.isEmpty()) {
            System.out.println("Hiç bilet bulunamadı.");
        } else {
            System.out.println("Tüm biletler:");
            for (Ticket t : tickets) {
                System.out.println(t);
            }
        }
    }

    // 🎯 3. UPDATE: ganti seat tiket
    public void updateTicketSeat(int ticketId, String newSeat) {
        Ticket t = ticketRepo.findById(ticketId);
        if (t != null) {
            // cek seat sudah terisi
            for (Ticket ticket : ticketRepo.getAll()) {
                if (ticket.getBusId() == t.getBusId() && ticket.getSeat().equalsIgnoreCase(newSeat)) {
                    System.out.println("Bu koltuk dolu!");
                    return;
                }
            }
            t.setSeat(newSeat);
            System.out.println("Bilet güncellendi: " + t);
        } else {
            System.out.println("Bilet bulunamadı!");
        }
    }

    // 🎯 4. DELETE / Cancel Ticket
    public void cancelTicket(int ticketId) {
        Ticket t = ticketRepo.findById(ticketId);
        if (t != null) {
            ticketRepo.remove(ticketId);
            System.out.println("Bilet iptal edildi: " + t);
        } else {
            System.out.println("Bilet bulunamadı!");
        }
    }

    // 🎯 5. FIND Ticket by ID
    public Ticket findTicketById(int id) {
        return ticketRepo.findById(id);
    }

    // 🎯 6. BONUS: cek seat kosong untuk bus tertentu
    public boolean isSeatAvailable(int busId, String seat) {
        for (Ticket t : ticketRepo.getAll()) {
            if (t.getBusId() == busId && t.getSeat().equalsIgnoreCase(seat)) {
                return false;
            }
        }
        return true;
    }
}