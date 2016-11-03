package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import pl.hypeapp.wykopolka.model.User;

public interface SignInView extends TiView {

    void loginAndSaveUser(User user, String accountKey);

    @CallOnMainThread
    void showSuccessLoginInfo();

    @CallOnMainThread
    void showLoading();

    @CallOnMainThread
    void showError();
}
