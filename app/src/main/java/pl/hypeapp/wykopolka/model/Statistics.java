package pl.hypeapp.wykopolka.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "statsCountAllBooks",
        "statsCountUserAddedBooks",
        "statsCountUserOwnedBooks",
        "statsCountUserWishlistedBooks",
        "statsCountAllTransfers",
        "statsCountAllUsers",
        "statsCountAllWishlistedBooks",
        "statsGetAverageBookPerUser",
        "statsGetMostWantedBook"
})
public class Statistics {

    @JsonProperty("statsCountAllBooks")
    private Integer statsCountAllBooks;
    @JsonProperty("statsCountUserAddedBooks")
    private Integer statsCountUserAddedBooks;
    @JsonProperty("statsCountUserOwnedBooks")
    private Integer statsCountUserOwnedBooks;
    @JsonProperty("statsCountUserWishlistedBooks")
    private Integer statsCountUserWishlistedBooks;
    @JsonProperty("statsCountAllTransfers")
    private Integer statsCountAllTransfers;
    @JsonProperty("statsCountAllUsers")
    private Integer statsCountAllUsers;
    @JsonProperty("statsCountAllWishlistedBooks")
    private Integer statsCountAllWishlistedBooks;
    @JsonProperty("statsGetAverageBookPerUser")
    private String statsGetAverageBookPerUser;
    @JsonProperty("statsGetMostWantedBook")
    private String statsGetMostWantedBook;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The statsCountAllBooks
     */
    @JsonProperty("statsCountAllBooks")
    public Integer getStatsCountAllBooks() {
        return statsCountAllBooks;
    }

    /**
     * @param statsCountAllBooks The statsCountAllBooks
     */
    @JsonProperty("statsCountAllBooks")
    public void setStatsCountAllBooks(Integer statsCountAllBooks) {
        this.statsCountAllBooks = statsCountAllBooks;
    }

    /**
     * @return The statsCountUserAddedBooks
     */
    @JsonProperty("statsCountUserAddedBooks")
    public Integer getStatsCountUserAddedBooks() {
        return statsCountUserAddedBooks;
    }

    /**
     * @param statsCountUserAddedBooks The statsCountUserAddedBooks
     */
    @JsonProperty("statsCountUserAddedBooks")
    public void setStatsCountUserAddedBooks(Integer statsCountUserAddedBooks) {
        this.statsCountUserAddedBooks = statsCountUserAddedBooks;
    }

    /**
     * @return The statsCountUserOwnedBooks
     */
    @JsonProperty("statsCountUserOwnedBooks")
    public Integer getStatsCountUserOwnedBooks() {
        return statsCountUserOwnedBooks;
    }

    /**
     * @param statsCountUserOwnedBooks The statsCountUserOwnedBooks
     */
    @JsonProperty("statsCountUserOwnedBooks")
    public void setStatsCountUserOwnedBooks(Integer statsCountUserOwnedBooks) {
        this.statsCountUserOwnedBooks = statsCountUserOwnedBooks;
    }

    /**
     * @return The statsCountUserWishlistedBooks
     */
    @JsonProperty("statsCountUserWishlistedBooks")
    public Integer getStatsCountUserWishlistedBooks() {
        return statsCountUserWishlistedBooks;
    }

    /**
     * @param statsCountUserWishlistedBooks The statsCountUserWishlistedBooks
     */
    @JsonProperty("statsCountUserWishlistedBooks")
    public void setStatsCountUserWishlistedBooks(Integer statsCountUserWishlistedBooks) {
        this.statsCountUserWishlistedBooks = statsCountUserWishlistedBooks;
    }

    /**
     * @return The statsCountAllTransfers
     */
    @JsonProperty("statsCountAllTransfers")
    public Integer getStatsCountAllTransfers() {
        return statsCountAllTransfers;
    }

    /**
     * @param statsCountAllTransfers The statsCountAllTransfers
     */
    @JsonProperty("statsCountAllTransfers")
    public void setStatsCountAllTransfers(Integer statsCountAllTransfers) {
        this.statsCountAllTransfers = statsCountAllTransfers;
    }

    /**
     * @return The statsCountAllUsers
     */
    @JsonProperty("statsCountAllUsers")
    public Integer getStatsCountAllUsers() {
        return statsCountAllUsers;
    }

    /**
     * @param statsCountAllUsers The statsCountAllUsers
     */
    @JsonProperty("statsCountAllUsers")
    public void setStatsCountAllUsers(Integer statsCountAllUsers) {
        this.statsCountAllUsers = statsCountAllUsers;
    }

    /**
     * @return The statsCountAllWishlistedBooks
     */
    @JsonProperty("statsCountAllWishlistedBooks")
    public Integer getStatsCountAllWishlistedBooks() {
        return statsCountAllWishlistedBooks;
    }

    /**
     * @param statsCountAllWishlistedBooks The statsCountAllWishlistedBooks
     */
    @JsonProperty("statsCountAllWishlistedBooks")
    public void setStatsCountAllWishlistedBooks(Integer statsCountAllWishlistedBooks) {
        this.statsCountAllWishlistedBooks = statsCountAllWishlistedBooks;
    }

    /**
     * @return The statsGetAverageBookPerUser
     */
    @JsonProperty("statsGetAverageBookPerUser")
    public String getStatsGetAverageBookPerUser() {
        return statsGetAverageBookPerUser;
    }

    /**
     * @param statsGetAverageBookPerUser The statsGetAverageBookPerUser
     */
    @JsonProperty("statsGetAverageBookPerUser")
    public void setStatsGetAverageBookPerUser(String statsGetAverageBookPerUser) {
        this.statsGetAverageBookPerUser = statsGetAverageBookPerUser;
    }

    /**
     * @return The statsGetMostWantedBook
     */
    @JsonProperty("statsGetMostWantedBook")
    public String getStatsGetMostWantedBook() {
        return statsGetMostWantedBook;
    }

    /**
     * @param statsGetMostWantedBook The statsGetMostWantedBook
     */
    @JsonProperty("statsGetMostWantedBook")
    public void setStatsGetMostWantedBook(String statsGetMostWantedBook) {
        this.statsGetMostWantedBook = statsGetMostWantedBook;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
