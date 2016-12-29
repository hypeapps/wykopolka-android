package pl.hypeapp.wykopolka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DemandQueue {

    @JsonProperty("Data")
    public List<Book> books;
    @JsonProperty("Demand")
    public List<PendingUser> pendingUsers;

    @JsonProperty("Data")
    public List<Book> getBooks() {
        return books;
    }

    @JsonProperty("Data")
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @JsonProperty("Demand")
    public List<PendingUser> getPendingUsers() {
        return pendingUsers;
    }

    @JsonProperty("Demand")
    public void setPendingUsers(List<PendingUser> pendingUsers) {
        this.pendingUsers = pendingUsers;
    }
}
