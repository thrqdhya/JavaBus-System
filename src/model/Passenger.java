package model;

public class Passenger {
    private int id;
    private String name;
    private String telefon;

    // Constructor
    public Passenger(int id, String isim, String telefon) {
        this.id = id;
        this.name = isim;
        this.telefon = telefon;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String isim) { this.name = isim; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    // toString untuk Debugging
    @Override
    public String toString() {
        return "Passenger{" + "id=" + id + ", isim='" + name + '\'' + ", telefon='" + telefon + '\'' + '}';
    }
}
