package pl.hypeapp.wykopolka.presenter;

import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.model.Pagination;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.AllBooksView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AllBooksPresenter extends TiPresenter<AllBooksView> {
    private static final String BOOKS_PER_PAGE = "15";
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private RetrofitComponent mRetrofitComponent;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);
    private WykopolkaApi mWykopolkaApi;
    private int currentPage;
    private List<Book> mBooks;

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        mWykopolkaApi = mRetrofit.create(WykopolkaApi.class);
        currentPage = 1;
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        allBooksCall(currentPage);
    }

    public void initRefreshData() {
        allBooksCall(currentPage);
    }

    private void allBooksCall(int currentPage) {
        String page = String.valueOf(currentPage);
        String apiSign = HashUtil.generateApiSign(page, BOOKS_PER_PAGE);
        rxHelper.manageSubscription(mWykopolkaApi.getAllBooks(currentPage, BOOKS_PER_PAGE, apiSign)
                .compose(RxTiPresenterUtils.<Pagination>deliverLatestToView(this))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Pagination>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Pagination pagination) {
                        Log.e("pagination", pagination.getPerPage() + " " + pagination.getData().size());
                        onLoadSuccessful(pagination.getData());
                    }
                })
        );

    }

    private void onLoadErrorHandling() {
//        mDemandQueue.getBooks().clear();
//        mDemandQueue.getPendingUsers().clear();
//        getView().setBookData(mDemandQueue);
//        getView().showError();
//        getView().stopLoadingAnimation();
//        getView().stopRefreshing();
    }

    private void onLoadSuccessful(List<Book> books) {
        mBooks = books;
        getView().setBookData(books);
        getView().hideError();
        getView().stopLoadingAnimation();
        getView().stopRefreshing();
    }
}