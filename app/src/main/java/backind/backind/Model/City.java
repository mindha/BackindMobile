package backind.backind.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("id_city")
    @Expose
    private String idCity;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("min(c.business_price)")
    @Expose
    private String minCBusinessPrice;

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMinCBusinessPrice() {
        return minCBusinessPrice;
    }

    public void setMinCBusinessPrice(String minCBusinessPrice) {
        this.minCBusinessPrice = minCBusinessPrice;
    }
}
