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

    // 🎯 1. Otobüs ekleme (Tambah Bus)
    public void addBus(Bus bus) {
        busRepository.add(bus);
        System.out.println("Otobüs basarıyla eklendi!");
    }

    // 🎯 2. Tüm otobüsleri listele
    public void getAllBuses() {
        List<Bus> buses = busRepository.getAll();

        if (buses.isEmpty()) {
            System.out.println("Hiç otobüs bulunamadı.");
        } else {
            for (Bus bus : buses) {
                System.out.println(bus);
            }
        }
    }

    // 🎯 3. Otobüs silme (Hapus Bus)
    public void removeBus(int id) {
        Bus bus = busRepository.findById(id);

        if (bus != null) {
            busRepository.remove(bus);
            System.out.println("Otobüs silindi.");
        } else {
            System.out.println("Otobüs bulunamadı!");
        }
    }

    // 🎯 4. ID ile otobüs bul
    public Bus findBusById(int id) {
        return busRepository.findById(id);
    }

    // 🎯 5. (BONUS) Kapasitas kursi
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