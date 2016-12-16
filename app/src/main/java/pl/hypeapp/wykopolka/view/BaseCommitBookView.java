package pl.hypeapp.wykopolka.view;

import android.graphics.Bitmap;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface BaseCommitBookView extends TiView {

    @CallOnMainThread
    void takeCoverPhoto();

    @CallOnMainThread
    void setCover(String coverUrl);

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
    void setRating(String rating);

    @CallOnMainThread
    void setQuality(String quality);

    Bitmap getCover();

    String getTitile();

    String getAuthor();

    String getDescription();

    String getIsbn();

    String getGenre();

    String getRating();

    String getQuality();

    @CallOnMainThread
    void showError(String message);

    @CallOnMainThread
    void hideError();
}