public class Bus {
    private int id;
    private String marka;
    private int koltukSayisi;

    // Constructor
    public Bus(int id, String marka, int koltukSayisi) {
        this.id = id;
        this.marka = marka;
        this.koltukSayisi = koltukSayisi;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMarka() { return marka; }
    public void setMarka(String marka) { this.marka = marka; }

    public int getKoltukSayisi() { return koltukSayisi; }
    public void setKoltukSayisi(int koltukSayisi) { this.koltukSayisi = koltukSayisi; }

    @Override
    public String toString() {
        return "Bus{" + "id=" + id + ", marka='" + marka + '\'' + ", koltukSayisi=" + koltukSayisi + '}';
    }
}
