package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import pl.hypeapp.wykopolka.model.WykopUser;

public interface SignInView extends TiView {

    void loginAndSaveUser(WykopUser user, String accountKey);

    @CallOnMainThread
    void showWykopolkaLoginInfo();

    @CallOnMainThread
    void handleError();
}
