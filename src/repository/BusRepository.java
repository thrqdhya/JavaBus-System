package repository;

import model.Bus;
import java.util.ArrayList;
import java.util.List;

public class BusRepository {
    private List<Bus> busList = new ArrayList<>();

    public void add(Bus bus) {
        busList.add(bus);
    }

    public void remove(int id) {
        busList.removeIf(bus -> bus.getId() == id);
    }

    public Bus findById(int id) {
        for (Bus bus : busList) {
            if (bus.getId() == id) {
                return bus;
            }
        }
        return null;
    }

    public List<Bus> getAll() {
        return busList;
    }
}