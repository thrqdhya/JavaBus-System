package model;

public class Ticket {
    private int id;
    private String yolcuAdi;
    private int koltukNo;
    private String tarih;

    // Constructor
    public Ticket(int id, String yolcuAdi, int koltukNo, String tarih) {
        this.id = id;
        this.yolcuAdi = yolcuAdi;
        this.koltukNo = koltukNo;
        this.tarih = tarih;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getYolcuAdi() { return yolcuAdi; }
    public void setYolcuAdi(String yolcuAdi) { this.yolcuAdi = yolcuAdi; }

    public int getKoltukNo() { return koltukNo; }
    public void setKoltukNo(int koltukNo) { this.koltukNo = koltukNo; }

    public String getTarih() { return tarih; }
    public void setTarih(String tarih) { this.tarih = tarih; }

    @Override
    public String toString() {
        return "Ticket{" + "id=" + id + ", yolcu='" + yolcuAdi + '\'' + ", koltuk=" + koltukNo + ", tanggal='" + tarih + '\'' + '}';
    }
}
