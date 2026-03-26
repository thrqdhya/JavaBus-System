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

    // 🎯 1. CREATE: Tambah Passenger
    public void addPassenger(Passenger passenger) {
        passengerRepository.add(passenger);
        System.out.println("Yolcu basarıyla eklendi: " + passenger);
    }

    // 🎯 2. READ: Get semua passenger
    public List<Passenger> getAllPassengersList() {
        return passengerRepository.getAll();
    }

    // Optional: print semua passenger
    public void printAllPassengers() {
        List<Passenger> passengers = passengerRepository.getAll();
        if (passengers.isEmpty()) {
            System.out.println("Hiç yolcu bulunamadı.");
        } else {
            System.out.println("Tüm yolcular:");
            for (Passenger p : passengers) {
                System.out.println(p);
            }
        }
    }

    // 🎯 3. UPDATE: Update nama passenger
    public void updatePassenger(int id, String newName) {
        Passenger p = passengerRepository.findById(id);
        if (p != null) {
            p.setName(newName);
            System.out.println("Yolcu güncellendi: " + p);
        } else {
            System.out.println("Yolcu bulunamadı!");
        }
    }

    // 🎯 4. DELETE: Hapus passenger
    public void removePassenger(int id) {
        Passenger p = passengerRepository.findById(id);
        if (p != null) {
            passengerRepository.remove(id);
            System.out.println("Yolcu silindi: " + p);
        } else {
            System.out.println("Yolcu bulunamadı!");
        }
    }

    // 🎯 5. Find by ID
    public Passenger findPassengerById(int id) {
        return passengerRepository.findById(id);
    }
}