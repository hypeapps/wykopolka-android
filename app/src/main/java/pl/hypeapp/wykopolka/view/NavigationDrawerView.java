package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import pl.hypeapp.wykopolka.model.UserStats;

public interface NavigationDrawerView extends TiView {

    @CallOnMainThread
    void setUserStats(UserStats userStats);

    @CallOnMainThread
    void saveUserStatsToPreferences(UserStats userStats);

    @CallOnMainThread
    void setUserStatsFromPreferences();

}