package backind.backind.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListNear {
    @SerializedName("loc")
    @Expose
    private BusinessDetails loc;
    @SerializedName("near")
    @Expose
    private List<Near> near = null;

    public BusinessDetails getLoc() {
        return loc;
    }

    public void setLoc(BusinessDetails loc) {
        this.loc = loc;
    }

    public List<Near> getNear() {
        return near;
    }

    public void setNear(List<Near> near) {
        this.near = near;
    }
}
