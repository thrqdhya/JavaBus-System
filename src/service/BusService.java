package service;

import model.Bus;
import repository.BusRepository;
import java.util.List;

public class BusService {

    private BusRepository busRepository;

    // Constructor
    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    // 🎯 1. CREATE: Tambah Bus
    public void addBus(Bus bus) {
        busRepository.add(bus);
        System.out.println("Otobüs basarıyla eklendi: " + bus);
    }

    // 🎯 2. READ: Get semua bus
    public List<Bus> getAllBuses() {
        return busRepository.getAll();
    }

    // Optional: print semua bus ke console
    public void printAllBuses() {
        List<Bus> buses = busRepository.getAll();
        if (buses.isEmpty()) {
            System.out.println("Hiç otobüs bulunamadı.");
        } else {
            System.out.println("Tüm otobüsler:");
            for (Bus bus : buses) {
                System.out.println(bus);
            }
        }
    }

    // 🎯 3. UPDATE: Update route & kapasitas bus
    public void updateBus(int id, String newRoute, int newCapacity) {
        Bus bus = busRepository.findById(id);
        if (bus != null) {
            bus.setRoute(newRoute);
            bus.setKoltukSayisi(newCapacity);
            System.out.println("Otobüs güncellendi: " + bus);
        } else {
            System.out.println("Otobüs bulunamadı!");
        }
    }

    // 🎯 4. DELETE: Hapus bus
    public void removeBus(int id) {
        Bus bus = busRepository.findById(id);
        if (bus != null) {
            busRepository.remove(id); // sesuai repository temanmu
            System.out.println("Otobüs silindi: " + bus);
        } else {
            System.out.println("Otobüs bulunamadı!");
        }
    }

    // 🎯 5. Find by ID
    public Bus findBusById(int id) {
        return busRepository.findById(id);
    }

    // 🎯 6. Bonus: Get kapasitas bus
    public int getBusCapacity(int id) {
        Bus bus = busRepository.findById(id);
        if (bus != null) {
            return bus.getKoltukSayisi();
        } else {
            System.out.println("Otobüs bulunamadı!");
            return -1;
        }
    }
}