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

    // 🎯 1. CREATE / BOOK TICKET
    public void bookTicket(int ticketId, int busId, int passengerId,
                           String from, String to, String date, String seat) {

        Bus bus = busRepo.findById(busId);
        Passenger passenger = passengerRepo.findById(passengerId);

        if (bus == null) {
            System.out.println("Bus not found!");
            return;
        }

        if (passenger == null) {
            System.out.println("Passenger not found!");
            return;
        }

        if (!isSeatAvailable(busId, seat)) {
            System.out.println("This seat is already booked!");
            return;
        }

        Ticket ticket = new Ticket(
                ticketId,
                busId,
                passengerId,
                from,
                to,
                date,
                seat,
                "CONFIRMED"
        );

        ticketRepo.add(ticket);

        System.out.println("Booking successful: " + ticket);
    }

    // 🎯 2. READ / Get All Tickets
    public List<Ticket> getAllTickets() {
        return ticketRepo.getAll();
    }

    // Print all tickets
    public void printAllTickets() {
        List<Ticket> tickets = ticketRepo.getAll();

        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
        } else {
            System.out.println("All tickets:");
            for (Ticket t : tickets) {
                System.out.println(t);
            }
        }
    }

    // 🎯 3. UPDATE: Change ticket seat
    public void updateTicketSeat(int ticketId, String newSeat) {

        Ticket t = ticketRepo.findById(ticketId);

        if (t == null) {
            System.out.println("Ticket not found!");
            return;
        }

        if (!isSeatAvailable(t.getBusId(), newSeat)) {
            System.out.println("This seat is already booked!");
            return;
        }

        t.setSeat(newSeat);
        System.out.println("Ticket updated: " + t);
    }

    // 🎯 4. DELETE / Cancel Ticket
    public void cancelTicket(int ticketId) {

        Ticket t = ticketRepo.findById(ticketId);

        if (t == null) {
            System.out.println("Ticket not found!");
            return;
        }

        ticketRepo.remove(ticketId);
        System.out.println("Ticket cancelled: " + t);
    }

    // 🎯 5. FIND Ticket by ID
    public Ticket findTicketById(int id) {
        return ticketRepo.findById(id);
    }

    // 🎯 6. CHECK seat availability
    public boolean isSeatAvailable(int busId, String seat) {

        for (Ticket t : ticketRepo.getAll()) {
            if (t.getBusId() == busId &&
                    t.getSeat().equalsIgnoreCase(seat)) {
                return false;
            }
        }

        return true;
    }
}