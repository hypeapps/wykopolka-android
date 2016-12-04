package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

import pl.hypeapp.wykopolka.model.Book;

public interface RandomBookView extends TiView {

    @CallOnMainThread
    @DistinctUntilChanged
    void showRandomBook(Book randomBook);

    @CallOnMainThread
    @DistinctUntilChanged
    void showRandomBookWithAnimation(Book book);

    @CallOnMainThread
    void hideRandomBook();

    @CallOnMainThread
    void nextRandomBook();

    @CallOnMainThread
    void showRandomizeAnimation();

    @CallOnMainThread
    void hideRandomizeAnimation();

    @CallOnMainThread
    void showError();

    @CallOnMainThread
    void hideError();

}