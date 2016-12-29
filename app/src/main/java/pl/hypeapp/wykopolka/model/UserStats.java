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
        "Wish",
        "Read",
        "Add"
})
public class UserStats {

    @JsonProperty("Wish")
    private Integer addedOnWishList;
    @JsonProperty("Read")
    private Integer readBooks;
    @JsonProperty("Add")
    private Integer addedBooks;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Wish")
    public Integer getAddedOnWishList() {
        return addedOnWishList;
    }

    @JsonProperty("Wish")
    public void setAddedOnWishList(Integer addedOnWishList) {
        this.addedOnWishList = addedOnWishList;
    }

    @JsonProperty("Read")
    public Integer getReadBooks() {
        return readBooks;
    }

    @JsonProperty("Read")
    public void setReadBooks(Integer readBooks) {
        this.readBooks = readBooks;
    }

    @JsonProperty("Add")
    public Integer getAddedBooks() {
        return addedBooks;
    }

    @JsonProperty("Add")
    public void setAddedBooks(Integer addedBooks) {
        this.addedBooks = addedBooks;
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
