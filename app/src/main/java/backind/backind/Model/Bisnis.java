
package backind.backind.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bisnis {

    @SerializedName("id_business")
    @Expose
    private int idBusiness;
    @SerializedName("id_menu")
    @Expose
    private int idMenu;
    @SerializedName("id_user")
    @Expose
    private int idUser;
    @SerializedName("id_city")
    @Expose
    private int idCity;
    @SerializedName("business_status")
    @Expose
    private int businessStatus;
    @SerializedName("id_business_details")
    @Expose
    private int idBusinessDetails;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("business_details")
    @Expose
    private HomestayDetails businessDetails;

    public int getIdBusiness() {
        return idBusiness;
    }

    public void setIdBusiness(int idBusiness) {
        this.idBusiness = idBusiness;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public int getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(int businessStatus) {
        this.businessStatus = businessStatus;
    }

    public int getIdBusinessDetails() {
        return idBusinessDetails;
    }

    public void setIdBusinessDetails(int idBusinessDetails) {
        this.idBusinessDetails = idBusinessDetails;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public HomestayDetails getBusinessDetails() {
        return businessDetails;
    }

    public void setBusinessDetails(HomestayDetails businessDetails) {
        this.businessDetails = businessDetails;
    }

}

