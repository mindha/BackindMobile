
package backind.backind.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessDetails {

    @SerializedName("id_business_details")
    @Expose
    private int idBusinessDetails;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("business_email")
    @Expose
    private String businessEmail;
    @SerializedName("business_address")
    @Expose
    private String businessAddress;
    @SerializedName("business_lat")
    @Expose
    private String businessLat;
    @SerializedName("business_lang")
    @Expose
    private String businessLang;
    @SerializedName("business_phone")
    @Expose
    private String businessPhone;
    @SerializedName("business_open_time")
    @Expose
    private String businessOpenTime;
    @SerializedName("business_close_time")
    @Expose
    private String businessCloseTime;
    @SerializedName("business_price")
    @Expose
    private int businessPrice;
    @SerializedName("business_desc")
    @Expose
    private String businessDesc;
    @SerializedName("business_profile_pict")
    @Expose
    private String businessProfilePict;
    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public int getIdBusinessDetails() {
        return idBusinessDetails;
    }

    public void setIdBusinessDetails(int idBusinessDetails) {
        this.idBusinessDetails = idBusinessDetails;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessLat() {
        return businessLat;
    }

    public void setBusinessLat(String businessLat) {
        this.businessLat = businessLat;
    }

    public String getBusinessLang() {
        return businessLang;
    }

    public void setBusinessLang(String businessLang) {
        this.businessLang = businessLang;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getBusinessOpenTime() {
        return businessOpenTime;
    }

    public void setBusinessOpenTime(String businessOpenTime) {
        this.businessOpenTime = businessOpenTime;
    }

    public String getBusinessCloseTime() {
        return businessCloseTime;
    }

    public void setBusinessCloseTime(String businessCloseTime) {
        this.businessCloseTime = businessCloseTime;
    }

    public int getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(int businessPrice) {
        this.businessPrice = businessPrice;
    }

    public String getBusinessDesc() {
        return businessDesc;
    }

    public void setBusinessDesc(String businessDesc) {
        this.businessDesc = businessDesc;
    }

    public String getBusinessProfilePict() {
        return businessProfilePict;
    }

    public void setBusinessProfilePict(String businessProfilePict) {
        this.businessProfilePict = businessProfilePict;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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

    public BusinessDetails(String businessName, int businessPrice, String businessProfilePict) {
        this.businessName = businessName;
        this.businessPrice = businessPrice;
        this.businessProfilePict = businessProfilePict;
    }
}

