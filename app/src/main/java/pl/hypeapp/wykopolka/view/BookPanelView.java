package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface BookPanelView extends TiView {

    @CallOnMainThread
    void addBook();

    @CallOnMainThread
    void setPage(int page);
}