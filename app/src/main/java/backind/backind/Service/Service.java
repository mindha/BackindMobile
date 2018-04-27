package backind.backind.Service;

import java.util.List;

import backind.backind.Model.Business;
import backind.backind.Model.Data;
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
    Call<LoginResponse<Data>> login(@Field("email") String email, @Field("password") String password);

    @GET("getHomestay")
    Call<List<Business>> getDataHomestay();
    @GET("getTourism")
    Call<List<Business>> getDataTourism();

    @FormUrlEncoded
    @POST("login")
    Call<Login> logins(@Field("email") String email, @Field("password") String password);

}
