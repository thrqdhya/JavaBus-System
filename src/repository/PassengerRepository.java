package repository;

import model.Passenger;
import java.util.ArrayList;
import java.util.List;

public class PassengerRepository {
    private List<Passenger> passengerList = new ArrayList<>();

    public void add(Passenger passenger) {
        passengerList.add(passenger);
    }

    public void remove(int id) {
        passengerList.removeIf(p -> p.getId() == id);
    }

    public Passenger findById(int id) {
        for (Passenger p : passengerList) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public List<Passenger> getAll() {
        return passengerList;
    }
}