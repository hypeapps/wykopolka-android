package pl.hypeapp.wykopolka.presenter;

import android.net.Uri;
import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;

import pl.hypeapp.wykopolka.BuildConfig;
import pl.hypeapp.wykopolka.WykopApi;
import pl.hypeapp.wykopolka.model.User;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.SignInView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SignInPresenter extends TiPresenter<SignInView> {
    private final int PATH_APPKEY = 3;
    private final int PATH_LOGIN = 5;
    private final int PATH_ACCOUNT_KEY = 7;
    private final String APP_KEY = BuildConfig.APP_KEY;
    private final String WAPI_SECRET = BuildConfig.WAPI_SECRET;
    private final String WYKOP_LOGIN_URL = "http://a.wykop.pl/user/login/appkey/" + APP_KEY + "/format/json/output/clear/";
    private final String BASE_URL = "http://a.wykop.pl/";
    private Retrofit mRetrofit;

    public  void handleLogin(String url) {
         mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        String appkey = Uri.parse(url).getPathSegments().get(PATH_APPKEY);
        String username = Uri.parse(url).getPathSegments().get(PATH_LOGIN);
        String accountKey = Uri.parse(url).getPathSegments().get(PATH_ACCOUNT_KEY);
        Log.e("url para", " " + appkey + " " + username + " " + accountKey);

        String sign = HashUtil.md5(WAPI_SECRET + WYKOP_LOGIN_URL + accountKey + "," + username);
        Log.e("sign_md5 ", sign);

        WykopApi wykopApi = mRetrofit.create(WykopApi.class);
        Call<User> login = wykopApi.loginUser(APP_KEY, username, accountKey, sign);
        login.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("username ", response.body().getLogin());
                Log.e("userkey ", response.body().getUserkey());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
