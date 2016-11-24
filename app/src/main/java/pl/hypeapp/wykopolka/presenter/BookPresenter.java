package pl.hypeapp.wykopolka.presenter;

import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.BookView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BookPresenter extends TiPresenter<BookView> {

    private static final int BOOK_INDEX = 0;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);
    private String mAccountKey;
    private String mBookId;
    private WykopolkaApi mWykopolkaApi;
    private List<Book> book;
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    RetrofitComponent mRetrofitComponent;

    public BookPresenter(String accountKey, String bookId) {
        this.mAccountKey = accountKey;
        this.mBookId = bookId;
        Log.e("PRESNETER_BOOK", mAccountKey + " " + mBookId);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        mWykopolkaApi = mRetrofit.create(WykopolkaApi.class);
        Log.e("BOOK_PRESENTER base_URL", mRetrofit.baseUrl().toString());
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();

        getView().animateCardEnter();

        callBookInfo();
    }

    private void callBookInfo() {
        String apiSign = HashUtil.generateApiSign(mBookId);

        rxHelper.manageViewSubscription(
                mWykopolkaApi.getBook(mBookId, apiSign)
                        .compose(RxTiPresenterUtils.<List<Book>>deliverLatestToView(this))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<Book>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<Book> books) {
                                getView().setBookCover(books.get(BOOK_INDEX).getCover());
                                getView().setBookInfo(books.get(BOOK_INDEX));
                                setBookOwnedBy(books);
                            }
                        }));
    }

    private void setBookOwnedBy(List<Book> book) {
        Log.e("here", "here");
        String apiSignAddedBy = HashUtil.generateApiSign(book.get(BOOK_INDEX).getAddedBy());
        String apiSignOwnedBy = HashUtil.generateApiSign(book.get(BOOK_INDEX).getOwnedBy());

//        rxHelper.manageViewSubscription();

//        mWykopolkaApi.getLoginById(book.get(BOOK_INDEX).getOwnedBy(), apiSignOwnedBy).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.e("response", response.message() + " " + response.code());
//                try {
//                    Log.e("response", response.body().string());
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
//        rxHelper.manageViewSubscription(
//                mWykopolkaApi.getLoginById(book.get(BOOK_INDEX).getAddedBy(), apiSignAddedBy).enqueue();
//        );


    }
}