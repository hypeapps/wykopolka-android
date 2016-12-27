package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;

public interface WelcomeView extends TiView {

    void manageUserStartup();

    void showLoginFailedMessage();
}