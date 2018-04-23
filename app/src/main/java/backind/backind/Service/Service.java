package backind.backind.Service;

import backind.backind.Response.LoginResponse;
import backind.backind.Response.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Service {
    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> register(@Field("name") String name, @Field("email") String email, @Field("phone_number") String phone_number, @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);
}
