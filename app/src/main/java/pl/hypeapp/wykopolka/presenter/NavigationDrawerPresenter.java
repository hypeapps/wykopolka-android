package pl.hypeapp.wykopolka.presenter;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.model.UserStats;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.NavigationDrawerView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NavigationDrawerPresenter extends TiPresenter<NavigationDrawerView> {
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private String mAccountKey;
    private WykopolkaApi mWykopolkaApi;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    public NavigationDrawerPresenter(String accountKey) {
        this.mAccountKey = accountKey;
    }

    @Override
    protected void onCreate() {
        RetrofitComponent mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        mWykopolkaApi = mRetrofit.create(WykopolkaApi.class);
        super.onCreate();
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        getView().setUserStatsFromPreferences();
        userStatsCall(mAccountKey);
    }

    private void userStatsCall(String accountKey) {
        String apiSign = HashUtil.generateApiSign(accountKey);

        rxHelper.manageViewSubscription(
                mWykopolkaApi.getUserStats(accountKey, apiSign)
                        .compose(RxTiPresenterUtils.<UserStats>deliverLatestToView(this))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<UserStats>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                OnErrorHandling();
                            }

                            @Override
                            public void onNext(UserStats userStats) {
                                OnNextHandling(userStats);
                            }
                        })
        );

    }

    private void OnNextHandling(UserStats userStats) {
        getView().setUserStats(userStats);
        getView().saveUserStatsToPreferences(userStats);
    }

    private void OnErrorHandling() {
        getView().setUserStatsFromPreferences();
    }
}