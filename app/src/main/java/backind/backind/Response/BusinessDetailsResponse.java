package backind.backind.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import backind.backind.Model.ListBisnisDetail;

public class BusinessDetailsResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ListBisnisDetail data;

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

    public ListBisnisDetail getData() {
        return data;
    }

    public void setData(ListBisnisDetail data) {
        this.data = data;
    }

}
