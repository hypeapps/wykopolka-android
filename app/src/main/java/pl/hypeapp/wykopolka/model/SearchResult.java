package pl.hypeapp.wykopolka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchResult {
    @JsonProperty("booksByTitle")
    private List<Book> booksByTitle;
    @JsonProperty("booksByAuthor")
    private List<Book> booksByAuthor;
    @JsonProperty("booksByTag")
    private List<Book> booksByTag;

    @JsonProperty("booksByTitle")
    public List<Book> getBooksByTitle() {
        return booksByTitle;
    }

    @JsonProperty("booksByTitle")
    public void setBooksByTitle(List<Book> booksByTitle) {
        this.booksByTitle = booksByTitle;
    }

    @JsonProperty("booksByAuthor")
    public List<Book> getBooksByAuthor() {
        return booksByAuthor;
    }

    @JsonProperty("booksByAuthor")
    public void setBooksByAuthor(List<Book> booksByAuthor) {
        this.booksByAuthor = booksByAuthor;
    }

    @JsonProperty("booksByTag")
    public List<Book> getBooksByTag() {
        return booksByTag;
    }

    @JsonProperty("booksByTag")
    public void setBooksByTag(List<Book> booksByTag) {
        this.booksByTag = booksByTag;
    }

}
