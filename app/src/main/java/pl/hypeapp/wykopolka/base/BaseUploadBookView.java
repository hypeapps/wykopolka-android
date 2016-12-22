package pl.hypeapp.wykopolka.base;

import android.graphics.Bitmap;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import pl.hypeapp.wykopolka.model.Book;

public interface BaseUploadBookView extends TiView {

    @CallOnMainThread
    void takeCoverPhoto();

    @CallOnMainThread
    void setCover(String coverUrl);

    @CallOnMainThread
    void setCoverBitmap(Bitmap photo);

    @CallOnMainThread
    void setTitle(String titile);

    @CallOnMainThread
    void setAuthor(String author);

    @CallOnMainThread
    void setIsbn(String isbn);

    @CallOnMainThread
    void setGenre(String genre);

    @CallOnMainThread
    void setDescription(String description);

    @CallOnMainThread
    void setRating(int rating);

    @CallOnMainThread
    void setQuality(int quality);

    String getBookTitle();

    String getAuthor();

    String getDescription();

    String getIsbn();

    String getGenre();

    int getRating();

    int getQuality();

    @CallOnMainThread
    void showMessageInputEmpty(int messageIndex);

    @CallOnMainThread
    void showLoading();

    @CallOnMainThread
    void hideLoading();

    @CallOnMainThread
    void uploadingBookSuccessful(Book book);

    @CallOnMainThread
    void showError(String message);

    @CallOnMainThread
    void showUploadError();
}