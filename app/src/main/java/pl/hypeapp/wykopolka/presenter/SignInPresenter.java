package pl.hypeapp.wykopolka.presenter;

import android.net.Uri;
import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.BuildConfig;
import pl.hypeapp.wykopolka.model.User;
import pl.hypeapp.wykopolka.network.api.WykopApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.SignInView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SignInPresenter extends TiPresenter<SignInView> {
    private static final String APP_KEY = BuildConfig.APP_KEY;
    private static final String WAPI_SECRET = BuildConfig.WAPI_SECRET;
    private static final String WYKOP_LOGIN_URL = "http://a.wykop.pl/user/login/appkey/" + APP_KEY + "/format/json/output/clear/";
    private static final int PATH_APPKEY = 3;
    private static final int PATH_LOGIN = 5;
    private static final int PATH_ACCOUNT_KEY = 7;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);
    private RetrofitComponent mRetrofitComponent;
    @Inject
    @Named("wykopApi")
    Retrofit mRetrofit;

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        Log.e("base_URL", mRetrofit.baseUrl().toString());
    }

    public void handleLogin(String url) {
        String appkey = Uri.parse(url).getPathSegments().get(PATH_APPKEY);
        String username = Uri.parse(url).getPathSegments().get(PATH_LOGIN);
        final String accountKey = Uri.parse(url).getPathSegments().get(PATH_ACCOUNT_KEY);
        Log.e("url para", " " + appkey + " " + username + " " + accountKey);

        String sign = HashUtil.md5(WAPI_SECRET + WYKOP_LOGIN_URL + accountKey + "," + username);
        Log.e("sign_md5 ", sign);

        WykopApi wykopApi = mRetrofit.create(WykopApi.class);

        rxHelper.manageSubscription(
                wykopApi.loginUser(APP_KEY, username, accountKey, sign)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<User>() {
                            @Override
                            public void onCompleted() {
                                getView().showSuccessLoginInfo();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(User user) {
                                getView().loginAndSaveUser(user, accountKey);
                            }
                        }));
    }
}
