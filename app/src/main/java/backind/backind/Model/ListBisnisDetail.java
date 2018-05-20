package backind.backind.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListBisnisDetail {
    @SerializedName("business_details")
    @Expose
    private List<BusinessData> businessDetails = null;

    public List<BusinessData> getBusinessDetails() {
        return businessDetails;
    }

    public void setBusinessDetails(List<BusinessData> businessDetails) {
        this.businessDetails = businessDetails;
    }
}
