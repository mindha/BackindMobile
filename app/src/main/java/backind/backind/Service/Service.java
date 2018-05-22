package backind.backind.Service;

import backind.backind.Model.PaymentReceipt;
import backind.backind.Response.BaseResponse;
import backind.backind.Response.BusinessDetailsResponse;
import backind.backind.Response.BusinessResponse;
import backind.backind.Response.InvoiceResponse;
import backind.backind.Response.NearbyResponse;
import backind.backind.Response.CityResponse;
import backind.backind.Response.LoginResponse;
import backind.backind.Response.ProfileResponse;
import backind.backind.Response.RegisterResponse;
import backind.backind.Response.ReviewResponse;
import backind.backind.Response.TransaksiResponse;
import backind.backind.Response.UpdateCostResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface Service {
    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> register(@Field("name") String name, @Field("email") String email, @Field("phone_number") String phone_number, @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("postUpdateDataUser")
    Call<ProfileResponse> updateUser(@Field("name") String name, @Field("email") String email, @Field("phone_number") String phone_number);

    @GET("getMinCity")
    Call<CityResponse> getListCity();

    @GET()
    Call<BusinessResponse>getDataTourism(@Url String url);
    @GET()
    Call<BusinessResponse>getDataHomestay(@Url String url);

    @FormUrlEncoded
    @POST()
    Call<NearbyResponse>getNearby(@Url String url, @Field("business_price") int price);

    @GET()
    Call<BusinessDetailsResponse>getDetailPerBusiness(@Url String url);

    @GET()
    Call<InvoiceResponse>getTransaction(@Url String url);

    @FormUrlEncoded
    @POST
    Call<UpdateCostResponse>getUpdateCost(@Url String url, @Field("total_cost") int total_cost);

    @FormUrlEncoded
    @POST
    Call<ReviewResponse>postReview(@Url String url, @Field("id_business") int id_business, @Field("review") String review, @Field("response") String response, @Field("rating") int rating, @Field("id_user") int id_user);


    @POST
    @Multipart
    Call<PaymentReceipt>paymentReceipt(@Url String url, @Part("id_transaksi")RequestBody id_transaksi, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("postAddBooking")
    Call<TransaksiResponse> booking(@Field("tourism")int id_tourism, @Field("homestay")int id_homestay, @Field("checkin") String checkin, @Field("checkout")String checkout, @Field("checkin_tourism") String checkin_tourism, @Field("total_ticket") int total_ticket);

    @FormUrlEncoded
    @POST("updatePassword")
    Call<BaseResponse>updatePassword(@Field("password")String password);


}
