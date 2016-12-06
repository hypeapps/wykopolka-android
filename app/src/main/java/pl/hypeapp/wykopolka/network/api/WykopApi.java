package pl.hypeapp.wykopolka.network.api;

import pl.hypeapp.wykopolka.model.WykopUser;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;


public interface WykopApi {

    @FormUrlEncoded
    @POST("user/login/appkey/{appkey}/format/json/output/clear/")
    Observable<WykopUser> loginUser(@Path("appkey") String appkey, @Field("login") String username,
                                    @Field("accountkey") String accountkey, @Header("apisign") String sign);
}

