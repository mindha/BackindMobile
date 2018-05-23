package backind.backind.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import backind.backind.Model.PaymentReceipt;

public class EticketResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<List<PaymentReceipt>> data = null;

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

    public List<List<PaymentReceipt>> getData() {
        return data;
    }

    public void setData(List<List<PaymentReceipt>> data) {
        this.data = data;
    }
}
