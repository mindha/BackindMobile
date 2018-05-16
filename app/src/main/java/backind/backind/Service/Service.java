package backind.backind.Service;

import java.util.List;

import backind.backind.Model.BusinessResponse;
import backind.backind.Response.CityResponse;
import backind.backind.Response.Login;
import backind.backind.Response.LoginResponse;
import backind.backind.Response.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Service {
    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> register(@Field("name") String name, @Field("email") String email, @Field("phone_number") String phone_number, @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @GET("getMinCity")
    Call<CityResponse> getListCity();

    @GET("getHomestay")
    Call<BusinessResponse>getDataHomestay();
//    Call<List<BusinessResponse>> getDataHomestay();
    @GET("getTourism")
    Call<BusinessResponse>getDataTourism();
//    Call<List<BusinessResponse>> getDataTourism();

    @FormUrlEncoded
    @POST("login")
    Call<Login> logins(@Field("email") String email, @Field("password") String password);

}
