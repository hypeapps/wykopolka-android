package pl.hypeapp.wykopolka.presenter;

import net.grandcentrix.thirtyinch.TiPresenter;

import pl.hypeapp.wykopolka.view.BookPanelView;

public class BookPanelPresenter extends TiPresenter<BookPanelView> {
    private int mPage;

    public BookPanelPresenter(int page) {
        mPage = page;
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        if (getView() != null) {
            getView().setPage(mPage);
        }
    }
}