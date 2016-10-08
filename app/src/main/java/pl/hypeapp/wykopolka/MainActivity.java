package pl.hypeapp.wykopolka;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import pl.hypeapp.wykopolka.model.User;
import pl.hypeapp.wykopolka.util.HashUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {
    final String BASE_URL = "http://a.wykop.pl/";
    final String WAPI_SECRET = BuildConfig.WAPI_SECRET;
    final String APP_KEY = BuildConfig.APP_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebClient webClient = new WebClient();
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(webClient);
        myWebView.loadUrl("http://a.wykop.pl/user/connect/appkey/" + APP_KEY);

        String username = "kWeb24";
        String accountKey = null;
        String url = "http://a.wykop.pl/user/login/appkey/" + APP_KEY + "/format/json/output/clear/";

        String sign = HashUtil.md5(WAPI_SECRET + url + accountKey + "," + username);
        Log.e("sign_md5 ", sign);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        WykopApi wykopApi = retrofit.create(WykopApi.class);

        Log.e("request_url", wykopApi.loginUser(APP_KEY, username, accountKey, sign).request().url().toString());

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
