package pl.hypeapp.wykopolka.presenter;

import android.net.Uri;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.BuildConfig;
import pl.hypeapp.wykopolka.model.RegisterStatus;
import pl.hypeapp.wykopolka.model.WykopUser;
import pl.hypeapp.wykopolka.network.api.WykopApi;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
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
    private String mUsername;
    private String mAccountKey;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);
    private RetrofitComponent mRetrofitComponent;
    private WykopUser mWykopUser;
    @Inject
    @Named("wykopApi")
    Retrofit mWykopRetrofit;
    @Inject
    @Named("wykopolkaApi")
    Retrofit mWykopolkaRetrofit;

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
    }

    public void handleWykopLogin(String url) {
        getView().showWykopolkaLoginInfo();
        final String appkey = Uri.parse(url).getPathSegments().get(PATH_APPKEY);
        mUsername = Uri.parse(url).getPathSegments().get(PATH_LOGIN);
        mAccountKey = Uri.parse(url).getPathSegments().get(PATH_ACCOUNT_KEY);
        String apiSign = HashUtil.md5(WAPI_SECRET + WYKOP_LOGIN_URL + mAccountKey + "," + mUsername);

        WykopApi wykopApi = mWykopRetrofit.create(WykopApi.class);
        rxHelper.manageSubscription(
                wykopApi.loginUser(APP_KEY, mUsername, mAccountKey, apiSign)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<WykopUser>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                onErrorHandling();
                            }

                            @Override
                            public void onNext(WykopUser user) {
                                mWykopUser = user;
                                handleWykopolkaSignIn(mUsername, mAccountKey);
                            }
                        }));
    }

    private void handleWykopolkaSignIn(final String login, final String token) {
        String apiSign = HashUtil.generateApiSign(login, token);
        WykopolkaApi wykopolkaApi = mWykopolkaRetrofit.create(WykopolkaApi.class);

        rxHelper.manageSubscription(wykopolkaApi.handleSignIn(login, token, apiSign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterStatus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorHandling();
                    }

                    @Override
                    public void onNext(RegisterStatus registerStatus) {
                        handleSuccessLogin(registerStatus);
                    }
                })
        );
    }

    private void handleSuccessLogin(RegisterStatus status) {
        if (status.getRegisterStatus()) {
            getView().loginAndSaveUser(mWykopUser, mAccountKey);
        }
    }

    public void onErrorHandling() {
        getView().handleError();
    }
}
