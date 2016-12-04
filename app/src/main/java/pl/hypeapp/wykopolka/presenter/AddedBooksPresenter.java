package pl.hypeapp.wykopolka.presenter;

import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.BookListView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddedBooksPresenter extends TiPresenter<BookListView> {

    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private RetrofitComponent mRetrofitComponent;
    private String mAccountKey;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    public AddedBooksPresenter(String accountKey) {
        this.mAccountKey = accountKey;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        Log.e("base_URL", mRetrofit.baseUrl().toString());
        testApi();
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        loadAddedBooks(mAccountKey);
    }


    private void testApi() {
//        String apiSign = HashUtil.generateApiSign(mAccountKey);
//        WykopolkaApi wykopolkaApi = mRetrofit.create(WykopolkaApi.class);
//        Call<ResponseBody> call = wykopolkaApi.getMyBooks(mAccountKey, apiSign);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Log.d("response", "onResponse: " + response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//        Call<ResponseBody> call = wykopolkaApi.getRandomBook(mAccountKey, apiSign);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Log.e("asd", "onResponse: " +  response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//        Call<ResponseBody> call = wykopolkaApi.getSelectedBooks(mAccountKey, "2", apiSign);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
////                Log.e("message",  " " + String.valueOf(response.code()));
//                try {
//                    Log.e("selected ", response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//        Call<Statistics> call = wykopolkaApi.getGlobalStats(mAccountKey, apiSign);
//        call.enqueue(new Callback<Statistics>() {
//            @Override
//            public void onResponse(Call<Statistics> call, Response<Statistics> response) {
//                Log.e("stats", " " + response.body().getStatsGetMostWantedBook());
//            }
//
//            @Override
//            public void onFailure(Call<Statistics> call, Throwable t) {
//
//            }
//        });
    }

    private void loadAddedBooks(String accountKey) {
        Log.e("acc_load_data", accountKey);
        String sign = HashUtil.generateApiSign(accountKey);
        WykopolkaApi wykopolkaApi = mRetrofit.create(WykopolkaApi.class);

        rxHelper.manageSubscription(
                wykopolkaApi.getUserAddedBooks(accountKey, sign)
                        .compose(RxTiPresenterUtils.<List<Book>>deliverLatestToView(this))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<Book>>() {
                            @Override
                            public void onCompleted() {
                                getView().stopLoadingAnimation();
                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().showError();
                                getView().setBookData(Collections.<Book>emptyList());
                            }

                            @Override
                            public void onNext(List<Book> books) {
                                getView().setBookData(books);
                                getView().hideError();
                            }
                        }));
    }

    public void initRefreshData() {
        loadAddedBooks(mAccountKey);
    }

}