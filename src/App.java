import model.*;
import repository.*;
import service.*;

public class App {
    public static void main(String[] args) {

        // Repository
        BusRepository busRepo = new BusRepository();
        PassengerRepository passengerRepo = new PassengerRepository();
        TicketRepository ticketRepo = new TicketRepository();

        // Service
        BusService busService = new BusService(busRepo);
        PassengerService passengerService = new PassengerService(passengerRepo);
        TicketService ticketService = new TicketService(ticketRepo);

        // 🔥 TEST DATA

        // Bus
        Bus bus1 = new Bus(1, "Mercedes", 40);
        busService.addBus(bus1);

        // Passenger
        Passenger p1 = new Passenger(1, "Ali", "123456");
        passengerService.addPassenger(p1);

        // Ticket
        Ticket t1 = new Ticket(1, "Ali", 5, "2026-03-25");
        Ticket t2 = new Ticket(2, "Veli", 5, "2026-03-25");

        ticketService.createTicket(t1);
        ticketService.createTicket(t2); // harusnya gagal

        // Output
        System.out.println("\n--- BUSES ---");
        busService.getAllBuses();

        System.out.println("\n--- PASSENGERS ---");
        passengerService.getAllPassengers();

        System.out.println("\n--- TICKETS ---");
        ticketService.getAllTickets();

        System.out.println("\n--- CANCEL TICKET ---");
        ticketService.cancelTicket(1);

        System.out.println("\n--- TICKETS AFTER CANCEL ---");
        ticketService.getAllTickets();
        
    }
}