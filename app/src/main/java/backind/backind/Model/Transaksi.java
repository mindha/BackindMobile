package backind.backind.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaksi {
    @SerializedName("id_tourism")
    @Expose
    private String idTourism;
    @SerializedName("id_homestay")
    @Expose
    private Object idHomestay;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("checkin")
    @Expose
    private String checkin;
    @SerializedName("checkout")
    @Expose
    private String checkout;
    @SerializedName("checkin_tourism")
    @Expose
    private Object checkinTourism;
    @SerializedName("total_ticket")
    @Expose
    private String totalTicket;
    @SerializedName("total_cost")
    @Expose
    private Integer totalCost;
    @SerializedName("duedate")
    @Expose
    private String duedate;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id_booking")
    @Expose
    private Integer idBooking;

    @SerializedName("tourism")
    @Expose
    private BusinessDetails tourism;
    @SerializedName("homestay")
    @Expose
    private BusinessDetails homestay;
    @SerializedName("user")
    @Expose
    private User user;

    public BusinessDetails getTourism() {
        return tourism;
    }

    public void setTourism(BusinessDetails tourism) {
        this.tourism = tourism;
    }

    public BusinessDetails getHomestay() {
        return homestay;
    }

    public void setHomestay(BusinessDetails homestay) {
        this.homestay = homestay;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIdTourism() {
        return idTourism;
    }

    public void setIdTourism(String idTourism) {
        this.idTourism = idTourism;
    }

    public Object getIdHomestay() {
        return idHomestay;
    }

    public void setIdHomestay(Object idHomestay) {
        this.idHomestay = idHomestay;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public Object getCheckinTourism() {
        return checkinTourism;
    }

    public void setCheckinTourism(Object checkinTourism) {
        this.checkinTourism = checkinTourism;
    }

    public String getTotalTicket() {
        return totalTicket;
    }

    public void setTotalTicket(String totalTicket) {
        this.totalTicket = totalTicket;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(Integer idBooking) {
        this.idBooking = idBooking;
    }
}
