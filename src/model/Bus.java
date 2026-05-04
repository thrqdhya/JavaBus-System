package model;

public class Bus {

    private int id;
    private String marka;
    private int koltukSayisi;

    private int fromCityId;
    private int toCityId;

    private String departureDate;
    private String departureTime;
    private String arrivalTime;

    private int price;

    // 🔥 CONSTRUCTOR BASIC (dipakai lama)
    public Bus(int id, String marka, int koltukSayisi) {
        this.id = id;
        this.marka = marka;
        this.koltukSayisi = koltukSayisi;
    }

    // 🔥 CONSTRUCTOR FULL (dipakai database)
    public Bus(int id, String marka, int koltukSayisi,
               int fromCityId, int toCityId,
               String departureDate,
               String departureTime,
               String arrivalTime,
               int price) {

        this.id = id;
        this.marka = marka;
        this.koltukSayisi = koltukSayisi;
        this.fromCityId = fromCityId;
        this.toCityId = toCityId;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }

    // =========================
    // GETTER
    // =========================

    public int getId() {
        return id;
    }

    public String getMarka() {
        return marka;
    }

    public int getKoltukSayisi() {
        return koltukSayisi;
    }

    public int getFromCityId() {
        return fromCityId;
    }

    public int getToCityId() {
        return toCityId;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getPrice() {
        return price;
    }

    // =========================
    // SETTER
    // =========================

    public void setId(int id) {
        this.id = id;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public void setKoltukSayisi(int koltukSayisi) {
        this.koltukSayisi = koltukSayisi;
    }

    public void setFromCityId(int fromCityId) {
        this.fromCityId = fromCityId;
    }

    public void setToCityId(int toCityId) {
        this.toCityId = toCityId;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // =========================
    // UTILITY (BONUS)
    // =========================

    public String getRoute() {
        return fromCityId + " → " + toCityId;
    }

    public String getTimeRange() {
        return departureTime + " → " + arrivalTime;
    }

    // =========================
    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", marka='" + marka + '\'' +
                ", seats=" + koltukSayisi +
                ", from=" + fromCityId +
                ", to=" + toCityId +
                ", date='" + departureDate + '\'' +
                ", time='" + departureTime + "-" + arrivalTime + '\'' +
                ", price=" + price +
                '}';
    }
}