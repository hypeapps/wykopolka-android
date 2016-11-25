package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;

import java.util.List;

import pl.hypeapp.wykopolka.model.Book;

public interface RandomBookView extends TiView {

    void showRandomBook(List<Book> book);

    void nextRandomBook();
}