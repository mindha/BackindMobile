package backind.backind.Rest;


import java.util.List;

import backind.backind.Model.Business;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Mindha on 15/01/2018.
 */

public interface RestApi {

    @GET("api/getHomestay")
    Call<List<Business>> getDataHomestay();
    @GET("api/getTourism")
    Call<List<Business>> getDataTourism();
}
