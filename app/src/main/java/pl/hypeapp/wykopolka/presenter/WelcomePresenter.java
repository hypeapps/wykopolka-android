package pl.hypeapp.wykopolka.presenter;

import net.grandcentrix.thirtyinch.TiPresenter;

import pl.hypeapp.wykopolka.view.WelcomeView;

public class WelcomePresenter extends TiPresenter<WelcomeView> {
    private boolean mIsLoginFailed;

    public WelcomePresenter(boolean isLoginFailed) {
        mIsLoginFailed = isLoginFailed;
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        if (getView() != null) {
            getView().manageUserStartup();
            if (mIsLoginFailed) {
                getView().showLoginFailedMessage();
            }
        }
    }
}