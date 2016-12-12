package pl.hypeapp.wykopolka.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.util.ImageUtil;
import pl.hypeapp.wykopolka.view.AddBookView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddBookPresenter extends TiPresenter<AddBookView> {
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private String mAccountkey;
    private WykopolkaApi mWykopolkaApi;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    public AddBookPresenter(String accountKey) {
        mAccountkey = accountKey;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        RetrofitComponent mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        mWykopolkaApi = mRetrofit.create(WykopolkaApi.class);
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
//        try {
//            uploadBook(mAccountkey);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
    }

    public void uploadBook(Bitmap cover, Book book) {
        String coverEncoded = ImageUtil.encodeToBase64(cover, Bitmap.CompressFormat.PNG, 100);
//        Book book = new Book();
//        book.setAuthor("DROP TABLE1");
//        book.setTitle("JUBON");
//        book.setDesc("TABS");
//        book.setIsbn("1234");
//        book.setGenre("CHUJNIA");
//        book.setRating("5");
//        book.setQuality("1");
        book.setCover(coverEncoded);

        String object;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Log.e("hashCode", "" + objectMapper.writeValueAsString(book));
            object = objectMapper.writeValueAsString(book);
            String sign = HashUtil.generateApiSign(mAccountkey, object);

            Call<ResponseBody> call = mWykopolkaApi.uploadBook(mAccountkey, object, sign);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.e("message", response.message() + " " + response.code());
                    try {
                        Log.e("response", response.body().string());
                    } catch (IOException e) {

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        } catch (JsonProcessingException ex) {

        }


//        rxHelper.manageSubscription(mWykopolkaApi.uploadBook(accountKey, object, sign)
//                .compose(RxTiPresenterUtils.<List<Book>>deliverLatestToView(this))
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe()););


    }


}