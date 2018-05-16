package backind.backind.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessData {
    @SerializedName("id_business")
    @Expose
    private Integer idBusiness;
    @SerializedName("id_menu")
    @Expose
    private String idMenu;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("id_city")
    @Expose
    private String idCity;
    @SerializedName("business_status")
    @Expose
    private String businessStatus;
    @SerializedName("id_business_details")
    @Expose
    private String idBusinessDetails;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("business_details")
    @Expose
    private BusinessDetails businessDetails;
    @SerializedName("review")
    @Expose
    private Object review;

    public Integer getIdBusiness() {
        return idBusiness;
    }

    public void setIdBusiness(Integer idBusiness) {
        this.idBusiness = idBusiness;
    }

    public String getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public String getIdBusinessDetails() {
        return idBusinessDetails;
    }

    public void setIdBusinessDetails(String idBusinessDetails) {
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

    public BusinessDetails getBusinessDetails() {
        return businessDetails;
    }

    public void setBusinessDetails(BusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
    }

    public Object getReview() {
        return review;
    }

    public void setReview(Object review) {
        this.review = review;
    }
}
