package backind.backind.Service;

import backind.backind.Response.BusinessDetailsResponse;
import backind.backind.Response.BusinessResponse;
import backind.backind.Response.NearbyResponse;
import backind.backind.Response.CityResponse;
import backind.backind.Response.LoginResponse;
import backind.backind.Response.ProfileResponse;
import backind.backind.Response.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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


}
