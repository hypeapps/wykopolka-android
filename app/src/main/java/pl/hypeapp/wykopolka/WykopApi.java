package pl.hypeapp.wykopolka;

import pl.hypeapp.wykopolka.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface WykopApi {

//    @GET("user/login/appkey/{appkey}/login/{username}/accountkey/{accountkey}")
//    Call<ResponseBody> loginUser(@Path("appkey") String appkey, @Path("username") String username,
//                                 @Path("accountkey") String accountkey, @Header("apisign") String sign);

//    @GET("user/login/appkey/{appkey}/login/{username}/accountkey/{accountkey}")
//    Call<ResponseBody> loginUser(@Path("appkey") String appkey, @Path("username") String username,
//                                 @Path("accountkey") String accountkey);
//
//    @POST("user/login/appkey/{appkey}/format/json/output/clear/")
//    Call<ResponseBody> loginUser(@Path("appkey") String appkey, @Body User user, @Header("apisign") String sign);

    @FormUrlEncoded
    @POST("user/login/appkey/{appkey}/format/json/output/clear/")
    Call<User> loginUser(@Path("appkey") String appkey, @Field("login") String username,
            @Field("accountkey") String accountkey, @Header("apisign") String sign);
}

