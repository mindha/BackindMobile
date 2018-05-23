package backind.backind.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessData implements Parcelable{
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
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("review")
    @Expose
    private List<Review> reviews = null;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public BusinessData() {
    }

    protected BusinessData(Parcel in) {
        if (in.readByte() == 0) {
            idBusiness = null;
        } else {
            idBusiness = in.readInt();
        }
        idMenu = in.readString();
        idUser = in.readString();
        idCity = in.readString();
        businessStatus = in.readString();
        idBusinessDetails = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<BusinessData> CREATOR = new Creator<BusinessData>() {
        @Override
        public BusinessData createFromParcel(Parcel in) {
            return new BusinessData(in);
        }

        @Override
        public BusinessData[] newArray(int size) {
            return new BusinessData[size];
        }
    };

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (idBusiness == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idBusiness);
        }
        parcel.writeString(idMenu);
        parcel.writeString(idUser);
        parcel.writeString(idCity);
        parcel.writeString(businessStatus);
        parcel.writeString(idBusinessDetails);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
    }
}
