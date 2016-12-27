package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

import pl.hypeapp.wykopolka.model.Book;

public interface BookView extends TiView {

    @CallOnMainThread
    void setBookDescription(Book book);

    @CallOnMainThread
    void setBookHolders(String addedBy, String ownedBy);

    @CallOnMainThread
    void setBookCover(String coverUrl);

    @CallOnMainThread
    void setWishStatusWilled();

    @CallOnMainThread
    void setWishStatusNotWilled();

    @CallOnMainThread
    void showEditButton();

    @CallOnMainThread
    void setWishIconDisabled();

    @CallOnMainThread
    void animateCardEnter();

    @CallOnMainThread
    void animateFabButtonEnter();

    @CallOnMainThread
    void showBookCard();

    @CallOnMainThread
    void dismissBookCard();

    @CallOnMainThread
    void editBookDescription();

    @CallOnMainThread
    void addBookToWishList();

    @CallOnMainThread
    void showSnackbarAddWishListSuccessful();

    @CallOnMainThread
    void showSnackbarRemovedFromWishList();

    @CallOnMainThread
    void showSnackbarWishListError();
}