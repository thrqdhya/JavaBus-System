package model;

public class Terminal {
    private int id;
    private String name;
    private int cityId;

    public Terminal(int id, String name, int cityId) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getCityId() { return cityId; }

    @Override
    public String toString() {
        return name;
    }
}
