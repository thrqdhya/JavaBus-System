package model;

public class Passenger {
    private int id;
    private String isim;
    private String telefon;

    // Constructor
    public Passenger(int id, String isim, String telefon) {
        this.id = id;
        this.isim = isim;
        this.telefon = telefon;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getIsim() { return isim; }
    public void setIsim(String isim) { this.isim = isim; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    // toString untuk Debugging
    @Override
    public String toString() {
        return "Passenger{" + "id=" + id + ", isim='" + isim + '\'' + ", telefon='" + telefon + '\'' + '}';
    }
}
