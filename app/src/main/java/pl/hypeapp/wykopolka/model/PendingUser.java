package pl.hypeapp.wykopolka.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ID",
        "Login",
        "Avatar",
        "Priv",
        "Name",
        "Address",
        "City",
        "Postal",
        "isRegistered",
        "wishId",
        "wasCalled",
        "callId"
})
public class PendingUser {
    @JsonProperty("ReceiverId")
    private Integer receiverId;
    @JsonProperty("Login")
    private String login;
    @JsonProperty("Avatar")
    private String avatar;
    @JsonProperty("Priv")
    private String priv;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("City")
    private String city;
    @JsonProperty("Postal")
    private String postal;
    @JsonProperty("isRegistered")
    private boolean isRegistered;
    @JsonProperty("wishId")
    private String wishId;
    @JsonProperty("wasCalled")
    private boolean wasCalled;
    @JsonProperty("callId")
    private String callId;

    @JsonProperty("ReceiverId")
    public Integer getReceiverId() {
        return receiverId;
    }

    @JsonProperty("ReceiverId")
    public void setID(Integer receiverId) {
        this.receiverId = receiverId;
    }

    @JsonProperty("Login")
    public String getLogin() {
        return login;
    }

    public String getLoginFormatted() {
        return "@" + login;
    }

    @JsonProperty("Login")
    public void setLogin(String login) {
        this.login = login;
    }

    @JsonProperty("Avatar")
    public String getAvatar() {
        return avatar;
    }

    @JsonProperty("Avatar")
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @JsonProperty("Priv")
    public String getPriv() {
        return priv;
    }

    @JsonProperty("Priv")
    public void setPriv(String priv) {
        this.priv = priv;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("Address")
    public void setAdress(String address) {
        this.address = address;
    }

    @JsonProperty("City")
    public String getCity() {
        return city;
    }

    @JsonProperty("City")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("Postal")
    public String getPostal() {
        return postal;
    }

    @JsonProperty("Postal")
    public void setPostal(String postal) {
        this.postal = postal;
    }

    @JsonProperty("isRegistered")
    public boolean getIsRegistered() {
        return isRegistered;
    }

    @JsonProperty("isRegistered")
    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    @JsonProperty("wishId")
    public String getWishId() {
        return wishId;
    }

    @JsonProperty("wishId")
    public void setWishId(String wishId) {
        this.wishId = wishId;
    }

    @JsonProperty("wasCalled")
    public boolean getWasCalled() {
        return wasCalled;
    }

    @JsonProperty("wasCalled")
    public void setWasCalled(boolean wasCalled) {
        this.wasCalled = wasCalled;
    }

    @JsonProperty("callId")
    public String getCallId() {
        return callId;
    }

    @JsonProperty("callId")
    public void setCallId(String callId) {
        this.callId = callId;
    }

}
