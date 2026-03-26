import model.*;
import repository.*;
import service.*;

import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Repository
        BusRepository busRepo = new BusRepository();
        PassengerRepository passengerRepo = new PassengerRepository();
        TicketRepository ticketRepo = new TicketRepository();

        // Service
        BusService busService = new BusService(busRepo);
        PassengerService passengerService = new PassengerService(passengerRepo);
        TicketService ticketService = new TicketService(ticketRepo, busRepo, passengerRepo);

        // 🔥 TEST DATA
        busService.addBus(new Bus(1, "Mercedes", 40));
        busService.addBus(new Bus(2, "Volvo", 30));

        passengerService.addPassenger(new Passenger(1, "Ali", "123456"));
        passengerService.addPassenger(new Passenger(2, "Veli", "654321"));

        // MAIN MENU LOOP
        while (true) {
            System.out.println("\n=== BUS & TICKET SYSTEM ===");
            System.out.println("1. Manage Buses");
            System.out.println("2. Manage Passengers");
            System.out.println("3. Manage Tickets");
            System.out.println("0. Exit");
            System.out.print("Select option: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                continue;
            }

            switch (option) {
                case 1 -> manageBuses(scanner, busService);
                case 2 -> managePassengers(scanner, passengerService);
                case 3 -> manageTickets(scanner, ticketService);
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    // === BUS MENU ===
    private static void manageBuses(Scanner scanner, BusService busService) {
        while (true) {
            System.out.println("\n--- BUS MENU ---");
            System.out.println("1. Add Bus");
            System.out.println("2. List Buses");
            System.out.println("3. Update Bus");
            System.out.println("4. Remove Bus");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            int opt;
            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                continue;
            }

            switch (opt) {
                case 1 -> {
                    System.out.print("Bus ID: "); int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Route/Brand: "); String route = scanner.nextLine();
                    System.out.print("Capacity: "); int capacity = Integer.parseInt(scanner.nextLine());
                    busService.addBus(new Bus(id, route, capacity));
                }
                case 2 -> busService.printAllBuses();
                case 3 -> {
                    System.out.print("Bus ID to update: "); int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("New Route/Brand: "); String newRoute = scanner.nextLine();
                    System.out.print("New Capacity: "); int newCapacity = Integer.parseInt(scanner.nextLine());
                    busService.updateBus(id, newRoute, newCapacity);
                }
                case 4 -> {
                    System.out.print("Bus ID to remove: "); int id = Integer.parseInt(scanner.nextLine());
                    busService.removeBus(id);
                }
                case 0 -> { return; }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    // === PASSENGER MENU ===
    private static void managePassengers(Scanner scanner, PassengerService passengerService) {
        while (true) {
            System.out.println("\n--- PASSENGER MENU ---");
            System.out.println("1. Add Passenger");
            System.out.println("2. List Passengers");
            System.out.println("3. Update Passenger Name");
            System.out.println("4. Remove Passenger");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            int opt;
            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                continue;
            }

            switch (opt) {
                case 1 -> {
                    System.out.print("Passenger ID: "); int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Name: "); String name = scanner.nextLine();
                    System.out.print("Phone: "); String phone = scanner.nextLine();
                    passengerService.addPassenger(new Passenger(id, name, phone));
                }
                case 2 -> passengerService.printAllPassengers();
                case 3 -> {
                    System.out.print("Passenger ID to update: "); int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("New Name: "); String newName = scanner.nextLine();
                    passengerService.updatePassenger(id, newName);
                }
                case 4 -> {
                    System.out.print("Passenger ID to remove: "); int id = Integer.parseInt(scanner.nextLine());
                    passengerService.removePassenger(id);
                }
                case 0 -> { return; }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    // === TICKET MENU ===
    private static void manageTickets(Scanner scanner, TicketService ticketService) {
        while (true) {
            System.out.println("\n--- TICKET MENU ---");
            System.out.println("1. Buy Ticket");
            System.out.println("2. List Tickets");
            System.out.println("3. Update Ticket Seat");
            System.out.println("4. Cancel Ticket");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            int opt;
            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                continue;
            }

            switch (opt) {
                case 1 -> {
                    System.out.print("Ticket ID: "); int ticketId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Bus ID: "); int busId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Passenger ID: "); int passengerId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Seat: "); String seat = scanner.nextLine();
                    ticketService.buyTicket(ticketId, busId, passengerId, seat);
                }
                case 2 -> ticketService.printAllTickets();
                case 3 -> {
                    System.out.print("Ticket ID to update: "); int ticketId = Integer.parseInt(scanner.nextLine());
                    System.out.print("New Seat: "); String newSeat = scanner.nextLine();
                    ticketService.updateTicketSeat(ticketId, newSeat);
                }
                case 4 -> {
                    System.out.print("Ticket ID to cancel: "); int ticketId = Integer.parseInt(scanner.nextLine());
                    ticketService.cancelTicket(ticketId);
                }
                case 0 -> { return; }
                default -> System.out.println("Invalid option!");
            }
        }
    }
}