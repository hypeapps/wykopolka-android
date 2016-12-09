package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;

import java.util.List;

import pl.hypeapp.wykopolka.model.Book;

public interface SearchBookView extends TiView {

    void setSearchedBooksByTitle(List<Book> books);

    void setSearchedBooksByAuthor(List<Book> books);

    void setSearchedBooksByGenre(List<Book> books);

}