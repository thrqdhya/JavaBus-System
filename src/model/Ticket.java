package model;

public class Ticket {

    private int id;
    private int busId;
    private int passengerId;
    private String fromCity;
    private String toCity;
    private String departureDate;
    private String seat;
    private String status;

    // Constructor (FULL)
    public Ticket(int id, int busId, int passengerId,
                  String fromCity, String toCity,
                  String departureDate, String seat, String status) {

        this.id = id;
        this.busId = busId;
        this.passengerId = passengerId;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.departureDate = departureDate;
        this.seat = seat;
        this.status = status;
    }

    // Constructor lama (BIAR GAK ERROR)
    public Ticket(int id, int busId, int passengerId, String seat) {
        this.id = id;
        this.busId = busId;
        this.passengerId = passengerId;
        this.seat = seat;
        this.status = "CONFIRMED"; // default
    }

    // Getter
    public int getId() { return id; }
    public int getBusId() { return busId; }
    public int getPassengerId() { return passengerId; }
    public String getFromCity() { return fromCity; }
    public String getToCity() { return toCity; }
    public String getDepartureDate() { return departureDate; }
    public String getSeat() { return seat; }
    public String getStatus() { return status; }

    // Setter
    public void setSeat(String seat) { this.seat = seat; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", busId=" + busId +
                ", passengerId=" + passengerId +
                ", from='" + fromCity + '\'' +
                ", to='" + toCity + '\'' +
                ", date='" + departureDate + '\'' +
                ", seat='" + seat + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}