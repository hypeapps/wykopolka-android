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
        "id",
        "login",
        "fullName",
        "adress",
        "city",
        "postal",
        "mail",
        "isMailConfirmed",
        "accountKeyToken",
        "accountStatus",
        "accountType",
        "userSign",
        "userKey",
        "avatarBig",
        "rankPoints",
        "password",
        "created_at",
        "updated_at"
})
public class User {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("login")
    private String login;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("adress")
    private String adress;
    @JsonProperty("city")
    private String city;
    @JsonProperty("postal")
    private String postal;
    @JsonProperty("mail")
    private String mail;
    @JsonProperty("isMailConfirmed")
    private Integer isMailConfirmed;
    @JsonProperty("notifyType")
    private Integer notifyType;
    @JsonProperty("accountKeyToken")
    private String accountKeyToken;
    @JsonProperty("accountStatus")
    private String accountStatus;
    @JsonProperty("accountType")
    private String accountType;
    @JsonProperty("userSign")
    private String userSign;
    @JsonProperty("userKey")
    private String userKey;
    @JsonProperty("avatarBig")
    private String avatarBig;
    @JsonProperty("rankPoints")
    private String rankPoints;
    @JsonProperty("password")
    private String password;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The login
     */
    @JsonProperty("login")
    public String getLogin() {
        return login;
    }

    /**
     * @param login The login
     */
    @JsonProperty("login")
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return The fullName
     */
    @JsonProperty("fullName")
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName The fullName
     */
    @JsonProperty("fullName")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return The adress
     */
    @JsonProperty("adress")
    public String getAdress() {
        return adress;
    }

    /**
     * @param adress The adress
     */
    @JsonProperty("adress")
    public void setAdress(String adress) {
        this.adress = adress;
    }

    /**
     * @return The city
     */
    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The postal
     */
    @JsonProperty("postal")
    public String getPostal() {
        return postal;
    }

    /**
     * @param postal The postal
     */
    @JsonProperty("postal")
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     * @return The mail
     */
    @JsonProperty("mail")
    public String getMail() {
        return mail;
    }

    /**
     * @param mail The mail
     */
    @JsonProperty("mail")
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return The isMailConfirmed
     */
    @JsonProperty("isMailConfirmed")
    public Integer getIsMailConfirmed() {
        return isMailConfirmed;
    }

    /**
     * @param isMailConfirmed The isMailConfirmed
     */
    @JsonProperty("isMailConfirmed")
    public void setIsMailConfirmed(Integer isMailConfirmed) {
        this.isMailConfirmed = isMailConfirmed;
    }

    /**
     * @return The notifyType
     */
    @JsonProperty("notifyType")
    public Integer getNotifyType() {
        return notifyType;
    }

    /**
     * @param notifyType The notifyType
     */
    @JsonProperty("notifyType")
    public void setNotifyType(Integer notifyType) {
        this.notifyType = notifyType;
    }
    /**
     * @return The accountKeyToken
     */
    @JsonProperty("accountKeyToken")
    public String getAccountKeyToken() {
        return accountKeyToken;
    }

    /**
     * @param accountKeyToken The accountKeyToken
     */
    @JsonProperty("accountKeyToken")
    public void setAccountKeyToken(String accountKeyToken) {
        this.accountKeyToken = accountKeyToken;
    }

    /**
     * @return The accountStatus
     */
    @JsonProperty("accountStatus")
    public String getAccountStatus() {
        return accountStatus;
    }

    /**
     * @param accountStatus The accountStatus
     */
    @JsonProperty("accountStatus")
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * @return The accountType
     */
    @JsonProperty("accountType")
    public String getAccountType() {
        return accountType;
    }

    /**
     * @param accountType The accountType
     */
    @JsonProperty("accountType")
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * @return The userSign
     */
    @JsonProperty("userSign")
    public String getUserSign() {
        return userSign;
    }

    /**
     * @param userSign The userSign
     */
    @JsonProperty("userSign")
    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    /**
     * @return The userKey
     */
    @JsonProperty("userKey")
    public String getUserKey() {
        return userKey;
    }

    /**
     * @param userKey The userKey
     */
    @JsonProperty("userKey")
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    /**
     * @return The avatarBig
     */
    @JsonProperty("avatarBig")
    public String getAvatarBig() {
        return avatarBig;
    }

    /**
     * @param avatarBig The avatarBig
     */
    @JsonProperty("avatarBig")
    public void setAvatarBig(String avatarBig) {
        this.avatarBig = avatarBig;
    }

    /**
     * @return The rankPoints
     */
    @JsonProperty("rankPoints")
    public String getRankPoints() {
        return rankPoints;
    }

    /**
     * @param rankPoints The rankPoints
     */
    @JsonProperty("rankPoints")
    public void setRankPoints(String rankPoints) {
        this.rankPoints = rankPoints;
    }

    /**
     * @return The password
     */
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return The createdAt
     */
    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The updatedAt
     */
    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt The updated_at
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
