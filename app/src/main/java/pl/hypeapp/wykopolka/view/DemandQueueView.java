package pl.hypeapp.wykopolka.view;

import android.support.v4.util.Pair;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.model.DemandQueue;
import pl.hypeapp.wykopolka.model.PendingUser;

public interface DemandQueueView extends TiView {

    @CallOnMainThread
    void setBookData(DemandQueue demandQueue);

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
    void showTransferDialog(Pair<Book, PendingUser> bookPendingUserPair);

    void dismissTransferDialog();

    void setDialogInLoading();

    @CallOnMainThread
    void dismissDialogLoading();

    @CallOnMainThread
    void showTransferError();
}
