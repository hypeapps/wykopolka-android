package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import java.util.List;

import pl.hypeapp.wykopolka.model.User;

public interface ProfileSettingsView extends TiView {

    @CallOnMainThread
    void setProfileAddress(User user);

    @CallOnMainThread
    void setProfileEmail(User user);

    @CallOnMainThread
    void setEmailNotificationsOn();

    @CallOnMainThread
    void setEmailNotificationsOff();

    @CallOnMainThread
    void setEmailNotificationsEnabled();

    @CallOnMainThread
    void setEmailNotificationsDisabled();

    @CallOnMainThread
    void setPmNotificationsOn();

    @CallOnMainThread
    void setPmNotificationsOff();

    List<String> getProfileAddress();

    String getProfileEmail();

    boolean getCheckedEmail();

    boolean getCheckedPm();

    @CallOnMainThread
    void showMessageAddressIncorrect();

    @CallOnMainThread
    void showMessageAddressEmailIncorrect();

    @CallOnMainThread
    void showMessageSaveSettingsFail();

    @CallOnMainThread
    void showMessageSaveSettingsSuccess();

    @CallOnMainThread
    void submitSettings();

    @CallOnMainThread
    void showSettingsCard();

    @CallOnMainThread
    void hideSettingsCard();

    @CallOnMainThread
    void showError();

    @CallOnMainThread
    void hideError();

    @CallOnMainThread
    void showLoading();

    @CallOnMainThread
    void hideLoading();

    @CallOnMainThread
    void retry();

    @CallOnMainThread
    void intentToPrivacyPolicy();

    @CallOnMainThread
    void logout();

}