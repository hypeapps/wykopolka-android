package pl.hypeapp.wykopolka.presenter;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.model.Statistics;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.StatisticView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class StatisticPresenter extends TiPresenter<StatisticView> {
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private String mAccountKey;
    private RetrofitComponent mRetrofitComponent;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    public StatisticPresenter(String accountKey) {
        this.mAccountKey = accountKey;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        loadStatistics(mAccountKey);
    }

    private void loadStatistics(String accountKey) {
        getView().startLoadingAnimation();
        String sign = HashUtil.generateApiSign(accountKey);
        WykopolkaApi wykopolkaApi = mRetrofit.create(WykopolkaApi.class);

        rxHelper.manageViewSubscription(
                wykopolkaApi.getGlobalStats(accountKey, sign)
                        .compose(RxTiPresenterUtils.<Statistics>deliverLatestToView(this))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Statistics>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                onErrorHandling();
                            }

                            @Override
                            public void onNext(Statistics statistics) {
                                onNextHandling(statistics);
                            }
                        })
        );
    }

    private void onNextHandling(Statistics statistics) {
        setStatisticsToView(statistics);
        getView().stopLoadingAnimation();
        getView().hideError();
    }

    private void onErrorHandling() {
        getView().stopLoadingAnimation();
        getView().hideStatistics();
        getView().showError();
    }

    private void setStatisticsToView(Statistics statistics) {
        if (statistics != null) {
            getView().showStatistics(statistics);
        }
    }

    public void loadAgain() {
        getView().hideError();
        loadStatistics(mAccountKey);
    }
}