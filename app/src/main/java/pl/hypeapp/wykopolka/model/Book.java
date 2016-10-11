package pl.hypeapp.wykopolka.model;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "id",
        "book_id",
        "title",
        "author",
        "genre",
        "isbn",
        "rating",
        "desc",
        "cover",
        "owned_by",
        "added_by",
        "isTmp",
        "quality",
        "created_at",
        "updated_at"
})
public class Book {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("book_id")
    private String bookId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("author")
    private String author;
    @JsonProperty("genre")
    private String genre;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("rating")
    private String rating;
    @JsonProperty("desc")
    private String desc;
    @JsonProperty("cover")
    private String cover;
    @JsonProperty("owned_by")
    private String ownedBy;
    @JsonProperty("added_by")
    private String addedBy;
    @JsonProperty("isTmp")
    private Integer isTmp;
    @JsonProperty("quality")
    private String quality;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The bookId
     */
    @JsonProperty("book_id")
    public String getBookId() {
        return bookId;
    }

    /**
     *
     * @param bookId
     * The book_id
     */
    @JsonProperty("book_id")
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    /**
     *
     * @return
     * The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The author
     */
    @JsonProperty("author")
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    @JsonProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     *
     * @return
     * The genre
     */
    @JsonProperty("genre")
    public String getGenre() {
        return genre;
    }

    /**
     *
     * @param genre
     * The genre
     */
    @JsonProperty("genre")
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     *
     * @return
     * The isbn
     */
    @JsonProperty("isbn")
    public String getIsbn() {
        return isbn;
    }

    /**
     *
     * @param isbn
     * The isbn
     */
    @JsonProperty("isbn")
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     *
     * @return
     * The rating
     */
    @JsonProperty("rating")
    public String getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    @JsonProperty("rating")
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     * The desc
     */
    @JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param desc
     * The desc
     */
    @JsonProperty("desc")
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @return
     * The cover
     */
    @JsonProperty("cover")
    public String getCover() {
        return cover;
    }

    /**
     *
     * @param cover
     * The cover
     */
    @JsonProperty("cover")
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     *
     * @return
     * The ownedBy
     */
    @JsonProperty("owned_by")
    public String getOwnedBy() {
        return ownedBy;
    }

    /**
     *
     * @param ownedBy
     * The owned_by
     */
    @JsonProperty("owned_by")
    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    /**
     *
     * @return
     * The addedBy
     */
    @JsonProperty("added_by")
    public String getAddedBy() {
        return addedBy;
    }

    /**
     *
     * @param addedBy
     * The added_by
     */
    @JsonProperty("added_by")
    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    /**
     *
     * @return
     * The isTmp
     */
    @JsonProperty("isTmp")
    public Integer getIsTmp() {
        return isTmp;
    }

    /**
     *
     * @param isTmp
     * The isTmp
     */
    @JsonProperty("isTmp")
    public void setIsTmp(Integer isTmp) {
        this.isTmp = isTmp;
    }

    /**
     *
     * @return
     * The quality
     */
    @JsonProperty("quality")
    public String getQuality() {
        return quality;
    }

    /**
     *
     * @param quality
     * The quality
     */
    @JsonProperty("quality")
    public void setQuality(String quality) {
        this.quality = quality;
    }

    /**
     *
     * @return
     * The createdAt
     */
    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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