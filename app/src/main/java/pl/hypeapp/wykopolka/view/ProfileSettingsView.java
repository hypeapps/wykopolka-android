package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;

import pl.hypeapp.wykopolka.model.User;

public interface ProfileSettingsView extends TiView {

    void setAddress(User user);

    void setSettings(User userSettings);

    void submitSettings();

    void showSettingsCard();

    void hideSettingsCard();

    void showError();

    void hideError();

    void showLoading();

    void hideLoading();

    void retry();

    void logout();

}