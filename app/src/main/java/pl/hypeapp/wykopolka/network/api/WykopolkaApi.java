package pl.hypeapp.wykopolka.network.api;



import java.util.List;

import okhttp3.ResponseBody;
import pl.hypeapp.wykopolka.model.Book;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface WykopolkaApi {

    /**
     * @param id
     * @param apisign
     * @return Book
     */
    @FormUrlEncoded
    @POST("wykopolka/public/api/request/book")
    Call<Book[]> getBook(@Field("id")String id, @Header("apisign")String apisign);

    @FormUrlEncoded
    @POST("wykopolka/public/api/request/book/find/by/author")
    Call<ResponseBody> findBookByAuthor(@Field("booksearch")String author, @Header("apisign")String apisign);

    @FormUrlEncoded
    @POST("wykopolka/public/api/request/user/books/added")
    Observable<List<Book>> getUserAddedBooks(@Field("accountkey")String accountKey, @Header("apisign")String apisign);

//    @FormUrlEncoded
//    @POST("wykopolka/public/api/request/user/books/added")
//    Call<ResponseBody> getUserAddedBooks(@Field("accountkey")String accountKey, @Header("apisign")String apisign);

//    @FormUrlEncoded
//    @POST("wykopolka/public/api/request/book")
//    Call<ResponseBody> getBooks(@Header("apisign")String apisign , @Field("cos") String cos);

}
