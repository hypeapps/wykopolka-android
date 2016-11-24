package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;

import pl.hypeapp.wykopolka.model.Book;

public interface BookView extends TiView {

    void setBookInfo(Book book);

    void setBookOwner(String addedBy, String ownedBy);

    void setWishlistStatus(boolean status);

    void setBookCover(String coverUrl);

    void animateCardEnter();

    void loadPdfBookCard();

    void editBookDescription();

    void addBookToWishlist();

    void showSnackbarBookWishlistedSuccesful();
}