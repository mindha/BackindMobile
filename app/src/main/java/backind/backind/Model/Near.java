package backind.backind.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Near {
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("id_business_details")
    @Expose
    private String idBusinessDetails;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("business_price")
    @Expose
    private String businessPrice;
    @SerializedName("id_menu")
    @Expose
    private String idMenu;


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIdBusinessDetails() {
        return idBusinessDetails;
    }

    public void setIdBusinessDetails(String idBusinessDetails) {
        this.idBusinessDetails = idBusinessDetails;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(String businessPrice) {
        this.businessPrice = businessPrice;
    }

    public String getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }
}
