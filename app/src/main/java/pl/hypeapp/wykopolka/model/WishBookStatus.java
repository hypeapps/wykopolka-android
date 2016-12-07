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
        "wishStatus",
        "wishAllowed"
})
public class WishBookStatus {

    @JsonProperty("wishStatus")
    private Boolean wishStatus;
    @JsonProperty("wishAllowed")
    private Boolean wishAllowed;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The wishStatus
     */
    @JsonProperty("wishStatus")
    public Boolean getWishStatus() {
        return wishStatus;
    }

    /**
     * @param wishStatus The wishStatus
     */
    @JsonProperty("wishStatus")
    public void setWishStatus(Boolean wishStatus) {
        this.wishStatus = wishStatus;
    }

    /**
     * @return The wishAllowed
     */
    @JsonProperty("wishAllowed")
    public Boolean getWishAllowed() {
        return wishAllowed;
    }

    /**
     * @param wishAllowed The wishAllowed
     */
    @JsonProperty("wishAllowed")
    public void setWishAllowed(Boolean wishAllowed) {
        this.wishAllowed = wishAllowed;
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
