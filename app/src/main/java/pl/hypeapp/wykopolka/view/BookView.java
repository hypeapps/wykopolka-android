package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

import pl.hypeapp.wykopolka.model.Book;

public interface BookView extends TiView {

    @DistinctUntilChanged
    @CallOnMainThread
    void setBookDescription(Book book);

    @DistinctUntilChanged
    @CallOnMainThread
    void setBookHolders(String addedBy, String ownedBy);

    @DistinctUntilChanged
    @CallOnMainThread
    void setBookCover(String coverUrl);

    @CallOnMainThread
    void setWishStatusWilled();

    @CallOnMainThread
    void setWishStatusNotWilled();

    @CallOnMainThread
    void setWishIconDisabled();

    @CallOnMainThread
    void animateCardEnter();

    @CallOnMainThread
    void animateFabButtonEnter();

    @CallOnMainThread
    void loadPdfBookCard();

    @CallOnMainThread
    void editBookDescription();

    @CallOnMainThread
    void addBookToWishlist();

    @CallOnMainThread
    void showSnackbarBookWishlistedSuccesful();
}