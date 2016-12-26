package pl.hypeapp.wykopolka.view;


import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import java.util.List;

import pl.hypeapp.wykopolka.base.BaseBookListView;
import pl.hypeapp.wykopolka.model.Book;

public interface AllBooksView extends BaseBookListView {

    @CallOnMainThread
    void onLoadedMoreDataFromApi(List<Book> books, int itemsCount);

}