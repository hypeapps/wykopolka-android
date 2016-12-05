package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import pl.hypeapp.wykopolka.model.Statistics;

public interface StatisticView extends TiView {

    @CallOnMainThread
    void showStatistics(Statistics statistics);

    @CallOnMainThread
    void hideStatistics();

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
}