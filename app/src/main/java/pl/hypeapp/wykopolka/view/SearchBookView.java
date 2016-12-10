package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import java.util.List;

import pl.hypeapp.wykopolka.model.Book;

public interface SearchBookView extends TiView {

    @CallOnMainThread
    void setSearchedBooksByTitle(List<Book> books);

    @CallOnMainThread
    void setSearchedBooksByAuthor(List<Book> books);

    @CallOnMainThread
    void setSearchedBooksByGenre(List<Book> books);

    @CallOnMainThread
    void resetResults();

    @CallOnMainThread
    void showSearchFailed();

    @CallOnMainThread
    void hideSearchFailed();

    @CallOnMainThread
    void showLoading();

    @CallOnMainThread
    void hideLoading();

    @CallOnMainThread
    void retry();

    @CallOnMainThread
    void showError();

    @CallOnMainThread
    void hideError();

}