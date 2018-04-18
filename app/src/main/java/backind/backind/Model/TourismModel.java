package backind.backind.Model;

public class TourismModel {
    private String name, thumnail;
    private int startFrom;

    public TourismModel(String name, int startFrom, String thumnail) {
        this.name = name;
        this.thumnail = thumnail;
        this.startFrom = startFrom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }

    public int getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(int startFrom) {
        this.startFrom = startFrom;
    }
}
