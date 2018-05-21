package backind.backind.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCost {
    @SerializedName("id_booking")
    @Expose
    private Transaksi idBooking;

    public Transaksi getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(Transaksi idBooking) {
        this.idBooking = idBooking;
    }

}
