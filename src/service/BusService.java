package service;

import model.Bus;
import repository.BusRepository;

import java.util.List;

public class BusService {

    private BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    // 🔥 ADD BUS
    public void addBus(Bus bus) {

        if (bus.getMarka() == null || bus.getMarka().isEmpty()) {
            System.out.println("Bus name cannot be empty!");
            return;
        }

        busRepository.add(bus);
        System.out.println("Bus added successfully!");
    }

    // 🔥 GET ALL
    public List<Bus> getAllBuses() {
        return busRepository.getAll();
    }

    // 🔥 FIND BY ID
    public Bus findBusById(int id) {
        return busRepository.findById(id);
    }

    // 🔥 DELETE
    public void removeBus(int id) {

        Bus bus = busRepository.findById(id);

        if (bus == null) {
            System.out.println("Bus not found!");
            return;
        }

        busRepository.remove(id);
        System.out.println("Bus removed!");
    }

    // 🔥 UPDATE (optional)
    public void updateBus(int id, String newMarka, int newCapacity) {

        Bus bus = busRepository.findById(id);

        if (bus == null) {
            System.out.println("Bus not found!");
            return;
        }

        bus.setMarka(newMarka);
        bus.setKoltukSayisi(newCapacity);

        // NOTE: kalau mau update DB, nanti tambahin query di repository
        System.out.println("Bus updated: " + bus);
    }

    // 🚀 🔥 FINAL SEARCH (NO DATE ANYMORE)
    public List<Bus> searchBuses(int fromCityId, int toCityId) {

        return busRepository.search(fromCityId, toCityId);
    }

    public void printAllBuses() {

        List<Bus> buses = busRepository.getAll();

        if (buses.isEmpty()) {
            System.out.println("No buses found.");
            return;
        }

        System.out.println("=== BUS LIST ===");

        for (Bus bus : buses) {
            System.out.println(
                    "ID: " + bus.getId() +
                            " | " + bus.getMarka() +
                            " | Capacity: " + bus.getKoltukSayisi() +
                            " | Price: " + bus.getPrice()
            );
        }
    }
}