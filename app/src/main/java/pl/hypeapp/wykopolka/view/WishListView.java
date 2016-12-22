package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import java.util.List;

import pl.hypeapp.wykopolka.model.Book;

public interface WishListView extends TiView {

    @CallOnMainThread
    void setBookData(List<Book> books);

    @CallOnMainThread
    void startLoadingAnimation();

    @CallOnMainThread
    void stopLoadingAnimation();

    @CallOnMainThread
    void showError();

    @CallOnMainThread
    void hideError();

    @CallOnMainThread
    void retry();

    @CallOnMainThread
    void stopRefreshing();

    @CallOnMainThread
    void showConfirmDialog(Book book);

    void dismissConfirmDialog();

    void setDialogInLoading();

    @CallOnMainThread
    void dismissDialogLoading();

    @CallOnMainThread
    void showConfirmationError();
}