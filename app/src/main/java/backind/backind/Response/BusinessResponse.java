
package backind.backind.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import backind.backind.Model.BusinessData;
import backind.backind.Response.BaseResponse;

public class BusinessResponse extends BaseResponse{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<BusinessData> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BusinessData> getData() {
        return data;
    }

    public void setData(List<BusinessData> data) {
        this.data = data;
    }
}

