package pl.hypeapp.wykopolka;

import okhttp3.ResponseBody;
import pl.hypeapp.wykopolka.model.Book;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WykopolkaApi {
    @FormUrlEncoded
    @POST("wykopolka/public/api/request/book")
    Call<Book[]> talkToMe(@Field("id")String chuj);
}
