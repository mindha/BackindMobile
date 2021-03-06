package backind.backind.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import backind.backind.Model.BusinessDetails;
import backind.backind.Model.Data;
import backind.backind.Model.ListNear;

public class NearbyResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ListNear data;


    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ListNear getData() {
        return data;
    }

    public void setData(ListNear data) {
        this.data = data;
    }
}
