package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import pl.hypeapp.wykopolka.base.BaseUploadBookView;

public interface EditBookView extends BaseUploadBookView {

    @CallOnMainThread
    void uploadEditedBook();

}