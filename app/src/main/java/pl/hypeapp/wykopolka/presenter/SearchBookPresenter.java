package pl.hypeapp.wykopolka.presenter;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.model.SearchResult;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.SearchBookView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchBookPresenter extends TiPresenter<SearchBookView> {
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private RetrofitComponent mRetrofitComponent;
    private String mQuery;
    private WykopolkaApi mWykopolkaApi;
    private SearchResult mSearchResult;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

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
        setSearchResultToView(mSearchResult);
    }

    public void searchByQuery(String query) {
        getView().showLoading();
        getView().resetResults();
        mQuery = query;

        String apiSign = HashUtil.generateApiSign(query);
        rxHelper.manageSubscription(
                mWykopolkaApi.searchByQuery(query, apiSign)
                        .compose(RxTiPresenterUtils.<SearchResult>deliverLatestToView(this))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<SearchResult>() {
                            @Override
                            public void onCompleted() {
                                getView().hideLoading();
                            }

                            @Override
                            public void onError(Throwable e) {
                                showErrorToView();
                            }

                            @Override
                            public void onNext(SearchResult searchResult) {
                                mSearchResult = searchResult;
                                setSearchResultToView(searchResult);
                            }
                        }));
    }

    public void searchByQueryOnIntent(String query) {
        mQuery = query;
        String apiSign = HashUtil.generateApiSign(query);
        rxHelper.manageSubscription(
                mWykopolkaApi.searchByQuery(query, apiSign)
                        .compose(RxTiPresenterUtils.<SearchResult>deliverLatestToView(this))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<SearchResult>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                showErrorToView();
                            }

                            @Override
                            public void onNext(SearchResult searchResult) {
                                mSearchResult = searchResult;
                                setSearchResultToView(searchResult);
                            }
                        }));
    }

    public void retry() {
        if (mQuery != null) {
            searchByQuery(mQuery);
        }
    }

    private void setSearchResultToView(SearchResult searchResult) {
        if (searchResult != null) {
            getView().hideError();
            getView().hideSearchFailed();
            getView().setSearchedBooksByTitle(searchResult.getBooksByTitle());
            getView().setSearchedBooksByAuthor(searchResult.getBooksByAuthor());
            getView().setSearchedBooksByGenre(searchResult.getBooksByTag());
            if (isSearchResultEmpty(searchResult)) {
                setSearchFailed();
            }
        }
    }

    private boolean isSearchResultEmpty(SearchResult searchResult) {
        return searchResult.getBooksByAuthor().size() == 0 && searchResult.getBooksByTitle().size() == 0
                && searchResult.getBooksByTag().size() == 0;
    }

    private void setSearchFailed() {
        getView().resetResults();
        getView().hideError();
        getView().showSearchFailed();
    }

    private void showErrorToView() {
        getView().hideSearchFailed();
        getView().hideError();
        getView().hideLoading();
        getView().resetResults();
        getView().showError();
    }

}