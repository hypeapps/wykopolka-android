package pl.hypeapp.wykopolka.presenter;

import android.content.Intent;

import net.grandcentrix.thirtyinch.TiPresenter;

import pl.hypeapp.wykopolka.view.DashboardView;

public class DashboardPresenter extends TiPresenter<DashboardView> {
    private static final int WISH_LIST_PAGE = 2;
    private Intent mIntent;

    public DashboardPresenter(Intent intent) {
        this.mIntent = intent;
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        setAdapterPage(mIntent);
    }

    private void setAdapterPage(Intent intent) {
        int page = intent.getIntExtra("page", 0);
        if (page == WISH_LIST_PAGE) {
            getView().setCurrentPage(page);
        }
    }
}