package model;

public class Ticket {

    private int id;
    private int busId;
    private int passengerId;
    private String seat;

    // Constructor
    public Ticket(int id, int busId, int passengerId, String seat) {
        this.id = id;
        this.busId = busId;
        this.passengerId = passengerId;
        this.seat = seat;
    }

    // Getter
    public int getId() {
        return id;
    }

    public int getBusId() {
        return busId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public String getSeat() {
        return seat;
    }

    // Setter
    public void setSeat(String seat) {
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", busId=" + busId +
                ", passengerId=" + passengerId +
                ", seat='" + seat + '\'' +
                '}';
    }
}