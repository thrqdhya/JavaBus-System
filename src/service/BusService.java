package service;

import model.Passenger;
import repository.PassengerRepository;

import java.util.List;

public class PassengerService {

    private PassengerRepository passengerRepository;

    // Constructor
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    // 🎯 1. Yolcu ekleme (Tambah Passenger)
    public void addPassenger(Passenger passenger) {
        passengerRepository.add(passenger);
        System.out.println("Yolcu başarıyla eklendi!");
    }

    // 🎯 2. Tüm yolcuları listele
    public void getAllPassengers() {
        List<Passenger> passengers = passengerRepository.getAll();

        if (passengers.isEmpty()) {
            System.out.println("Hiç yolcu bulunamadı.");
        } else {
            for (Passenger passenger : passengers) {
                System.out.println(passenger);
            }
        }
    }

    // 🎯 3. Yolcu silme (Hapus Passenger)
    public void removePassenger(int id) {
        Passenger passenger = passengerRepository.findById(id);

        if (passenger != null) {
            passengerRepository.remove(passenger);
            System.out.println("Yolcu silindi.");
        } else {
            System.out.println("Yolcu bulunamadı!");
        }
    }

    // 🎯 4. ID ile yolcu bul
    public Passenger findPassengerById(int id) {
        return passengerRepository.findById(id);
    }
}