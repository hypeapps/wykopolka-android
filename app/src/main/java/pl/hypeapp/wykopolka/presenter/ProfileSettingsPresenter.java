package pl.hypeapp.wykopolka.presenter;

import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.model.User;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.ProfileSettingsView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileSettingsPresenter extends TiPresenter<ProfileSettingsView> {
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);
    private String mAccountKey;
    private WykopolkaApi mWykopolkaApi;
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    RetrofitComponent mRetrofitComponent;

    public ProfileSettingsPresenter(String accountKey) {
        this.mAccountKey = accountKey;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        mWykopolkaApi = mRetrofit.create(WykopolkaApi.class);
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        loadUserSettings(mAccountKey);
    }

    private void loadUserSettings(String accountKey) {
        String apiSign = HashUtil.generateApiSign(accountKey);

        rxHelper.manageSubscription(
                mWykopolkaApi.getUser(accountKey, apiSign)
                        .compose(RxTiPresenterUtils.<User>deliverLatestToView(this))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<User>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                handleError();
                            }

                            @Override
                            public void onNext(User user) {
                                Log.e("user", user.getFullName());
                                setUserSettingsToView(user);
                            }
                        })
        );
    }

    private void setUserSettingsToView(User userSettings) {
        getView().hideError();
        getView().hideLoading();
        getView().setAddress(userSettings);
        getView().setSettings(userSettings);
        getView().showSettingsCard();
    }

    private void handleError() {
        getView().hideLoading();
        getView().hideSettingsCard();
        getView().showError();
    }

    public void loadSettingsAgain() {
        getView().hideError();
        getView().showLoading();
        loadUserSettings(mAccountKey);
    }

}