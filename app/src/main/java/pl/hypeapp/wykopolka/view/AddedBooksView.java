package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

import java.util.List;

import pl.hypeapp.wykopolka.model.Book;

public interface AddedBooksView extends TiView {

    @DistinctUntilChanged
    @CallOnMainThread
    void setBookData(List<Book> books);

    void onRefreshCompleted();
}