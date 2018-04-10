package backind.backind.Model;

public class Kota {
    private String name, thumnail;
    private int startFrom;

    public Kota(String name, int startFrom, String thumnail) {
        this.name = name;
        this.startFrom = startFrom;
        this.thumnail = thumnail;
    }

    public Kota() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(int startFrom) {
        this.startFrom = startFrom;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }
}
