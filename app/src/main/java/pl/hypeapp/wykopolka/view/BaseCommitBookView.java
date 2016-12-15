package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface BaseCommitBookView extends TiView {

    @CallOnMainThread
    void takeCoverPhoto();

    @CallOnMainThread
    void setCover(String coverUrl);

    @CallOnMainThread
    void setTitile(String titile);

    @CallOnMainThread
    void setAuthor(String author);

    @CallOnMainThread
    void setDescription(String description);

    @CallOnMainThread
    void setRating(String rating);

    @CallOnMainThread
    void setQuality(String quality);

    @CallOnMainThread
    void getCover();

    @CallOnMainThread
    void getTitile();

    @CallOnMainThread
    void getAuthor();

    @CallOnMainThread
    void getDescription();

    @CallOnMainThread
    void getRating();

    @CallOnMainThread
    void getQuality();

    @CallOnMainThread
    void showError(String message);

    @CallOnMainThread
    void hideError();
}