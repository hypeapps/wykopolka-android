package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface DashboardView extends TiView {

    @CallOnMainThread
    void setCurrentPage(int page);

    @CallOnMainThread
    void addBook();
}