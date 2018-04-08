package backind.backind.Model;

public class Kota {
    private String name;
    private int startFrom;
    private int thumnail;

    public Kota(String name, int startFrom, int thumnail) {
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

    public int getThumnail() {
        return thumnail;
    }

    public void setThumnail(int thumnail) {
        this.thumnail = thumnail;
    }
}
