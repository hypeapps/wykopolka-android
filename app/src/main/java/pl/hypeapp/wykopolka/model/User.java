package pl.hypeapp.wykopolka.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "login",
        "email",
        "public_email",
        "name",
        "www",
        "jabber",
        "gg",
        "city",
        "about",
        "author_group",
        "links_added",
        "links_published",
        "comments",
        "rank",
        "followers",
        "following",
        "entries",
        "entries_comments",
        "diggs",
        "buries",
        "groups",
        "related_links",
        "signup_date",
        "avatar",
        "avatar_big",
        "avatar_med",
        "avatar_lo",
        "is_observed",
        "is_blocked",
        "sex",
        "url",
        "violation_url",
        "userkey"
})
public class User {

    @JsonProperty("login")
    private String login;
    @JsonProperty("email")
    private String email;
    @JsonProperty("public_email")
    private String publicEmail;
    @JsonProperty("name")
    private String name;
    @JsonProperty("www")
    private String www;
    @JsonProperty("jabber")
    private String jabber;
    @JsonProperty("gg")
    private String gg;
    @JsonProperty("city")
    private String city;
    @JsonProperty("about")
    private String about;
    @JsonProperty("author_group")
    private Integer authorGroup;
    @JsonProperty("links_added")
    private Integer linksAdded;
    @JsonProperty("links_published")
    private Integer linksPublished;
    @JsonProperty("comments")
    private Integer comments;
    @JsonProperty("rank")
    private Integer rank;
    @JsonProperty("followers")
    private Integer followers;
    @JsonProperty("following")
    private Integer following;
    @JsonProperty("entries")
    private Integer entries;
    @JsonProperty("entries_comments")
    private Integer entriesComments;
    @JsonProperty("diggs")
    private Integer diggs;
    @JsonProperty("buries")
    private Integer buries;
    @JsonProperty("groups")
    private Integer groups;
    @JsonProperty("related_links")
    private Integer relatedLinks;
    @JsonProperty("signup_date")
    private String signupDate;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("avatar_big")
    private String avatarBig;
    @JsonProperty("avatar_med")
    private String avatarMed;
    @JsonProperty("avatar_lo")
    private String avatarLo;
    @JsonProperty("is_observed")
    private Object isObserved;
    @JsonProperty("is_blocked")
    private Object isBlocked;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("url")
    private String url;
    @JsonProperty("violation_url")
    private Object violationUrl;
    @JsonProperty("userkey")
    private String userkey;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The login
     */
    @JsonProperty("login")
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     * The login
     */
    @JsonProperty("login")
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return
     * The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The publicEmail
     */
    @JsonProperty("public_email")
    public String getPublicEmail() {
        return publicEmail;
    }

    /**
     *
     * @param publicEmail
     * The public_email
     */
    @JsonProperty("public_email")
    public void setPublicEmail(String publicEmail) {
        this.publicEmail = publicEmail;
    }

    /**
     *
     * @return
     * The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The www
     */
    @JsonProperty("www")
    public String getWww() {
        return www;
    }

    /**
     *
     * @param www
     * The www
     */
    @JsonProperty("www")
    public void setWww(String www) {
        this.www = www;
    }

    /**
     *
     * @return
     * The jabber
     */
    @JsonProperty("jabber")
    public String getJabber() {
        return jabber;
    }

    /**
     *
     * @param jabber
     * The jabber
     */
    @JsonProperty("jabber")
    public void setJabber(String jabber) {
        this.jabber = jabber;
    }

    /**
     *
     * @return
     * The gg
     */
    @JsonProperty("gg")
    public String getGg() {
        return gg;
    }

    /**
     *
     * @param gg
     * The gg
     */
    @JsonProperty("gg")
    public void setGg(String gg) {
        this.gg = gg;
    }

    /**
     *
     * @return
     * The city
     */
    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The about
     */
    @JsonProperty("about")
    public String getAbout() {
        return about;
    }

    /**
     *
     * @param about
     * The about
     */
    @JsonProperty("about")
    public void setAbout(String about) {
        this.about = about;
    }

    /**
     *
     * @return
     * The authorGroup
     */
    @JsonProperty("author_group")
    public Integer getAuthorGroup() {
        return authorGroup;
    }

    /**
     *
     * @param authorGroup
     * The author_group
     */
    @JsonProperty("author_group")
    public void setAuthorGroup(Integer authorGroup) {
        this.authorGroup = authorGroup;
    }

    /**
     *
     * @return
     * The linksAdded
     */
    @JsonProperty("links_added")
    public Integer getLinksAdded() {
        return linksAdded;
    }

    /**
     *
     * @param linksAdded
     * The links_added
     */
    @JsonProperty("links_added")
    public void setLinksAdded(Integer linksAdded) {
        this.linksAdded = linksAdded;
    }

    /**
     *
     * @return
     * The linksPublished
     */
    @JsonProperty("links_published")
    public Integer getLinksPublished() {
        return linksPublished;
    }

    /**
     *
     * @param linksPublished
     * The links_published
     */
    @JsonProperty("links_published")
    public void setLinksPublished(Integer linksPublished) {
        this.linksPublished = linksPublished;
    }

    /**
     *
     * @return
     * The comments
     */
    @JsonProperty("comments")
    public Integer getComments() {
        return comments;
    }

    /**
     *
     * @param comments
     * The comments
     */
    @JsonProperty("comments")
    public void setComments(Integer comments) {
        this.comments = comments;
    }

    /**
     *
     * @return
     * The rank
     */
    @JsonProperty("rank")
    public Integer getRank() {
        return rank;
    }

    /**
     *
     * @param rank
     * The rank
     */
    @JsonProperty("rank")
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     *
     * @return
     * The followers
     */
    @JsonProperty("followers")
    public Integer getFollowers() {
        return followers;
    }

    /**
     *
     * @param followers
     * The followers
     */
    @JsonProperty("followers")
    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    /**
     *
     * @return
     * The following
     */
    @JsonProperty("following")
    public Integer getFollowing() {
        return following;
    }

    /**
     *
     * @param following
     * The following
     */
    @JsonProperty("following")
    public void setFollowing(Integer following) {
        this.following = following;
    }

    /**
     *
     * @return
     * The entries
     */
    @JsonProperty("entries")
    public Integer getEntries() {
        return entries;
    }

    /**
     *
     * @param entries
     * The entries
     */
    @JsonProperty("entries")
    public void setEntries(Integer entries) {
        this.entries = entries;
    }

    /**
     *
     * @return
     * The entriesComments
     */
    @JsonProperty("entries_comments")
    public Integer getEntriesComments() {
        return entriesComments;
    }

    /**
     *
     * @param entriesComments
     * The entries_comments
     */
    @JsonProperty("entries_comments")
    public void setEntriesComments(Integer entriesComments) {
        this.entriesComments = entriesComments;
    }

    /**
     *
     * @return
     * The diggs
     */
    @JsonProperty("diggs")
    public Integer getDiggs() {
        return diggs;
    }

    /**
     *
     * @param diggs
     * The diggs
     */
    @JsonProperty("diggs")
    public void setDiggs(Integer diggs) {
        this.diggs = diggs;
    }

    /**
     *
     * @return
     * The buries
     */
    @JsonProperty("buries")
    public Integer getBuries() {
        return buries;
    }

    /**
     *
     * @param buries
     * The buries
     */
    @JsonProperty("buries")
    public void setBuries(Integer buries) {
        this.buries = buries;
    }

    /**
     *
     * @return
     * The groups
     */
    @JsonProperty("groups")
    public Integer getGroups() {
        return groups;
    }

    /**
     *
     * @param groups
     * The groups
     */
    @JsonProperty("groups")
    public void setGroups(Integer groups) {
        this.groups = groups;
    }

    /**
     *
     * @return
     * The relatedLinks
     */
    @JsonProperty("related_links")
    public Integer getRelatedLinks() {
        return relatedLinks;
    }

    /**
     *
     * @param relatedLinks
     * The related_links
     */
    @JsonProperty("related_links")
    public void setRelatedLinks(Integer relatedLinks) {
        this.relatedLinks = relatedLinks;
    }

    /**
     *
     * @return
     * The signupDate
     */
    @JsonProperty("signup_date")
    public String getSignupDate() {
        return signupDate;
    }

    /**
     *
     * @param signupDate
     * The signup_date
     */
    @JsonProperty("signup_date")
    public void setSignupDate(String signupDate) {
        this.signupDate = signupDate;
    }

    /**
     *
     * @return
     * The avatar
     */
    @JsonProperty("avatar")
    public String getAvatar() {
        return avatar;
    }

    /**
     *
     * @param avatar
     * The avatar
     */
    @JsonProperty("avatar")
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     *
     * @return
     * The avatarBig
     */
    @JsonProperty("avatar_big")
    public String getAvatarBig() {
        return avatarBig;
    }

    /**
     *
     * @param avatarBig
     * The avatar_big
     */
    @JsonProperty("avatar_big")
    public void setAvatarBig(String avatarBig) {
        this.avatarBig = avatarBig;
    }

    /**
     *
     * @return
     * The avatarMed
     */
    @JsonProperty("avatar_med")
    public String getAvatarMed() {
        return avatarMed;
    }

    /**
     *
     * @param avatarMed
     * The avatar_med
     */
    @JsonProperty("avatar_med")
    public void setAvatarMed(String avatarMed) {
        this.avatarMed = avatarMed;
    }

    /**
     *
     * @return
     * The avatarLo
     */
    @JsonProperty("avatar_lo")
    public String getAvatarLo() {
        return avatarLo;
    }

    /**
     *
     * @param avatarLo
     * The avatar_lo
     */
    @JsonProperty("avatar_lo")
    public void setAvatarLo(String avatarLo) {
        this.avatarLo = avatarLo;
    }

    /**
     *
     * @return
     * The isObserved
     */
    @JsonProperty("is_observed")
    public Object getIsObserved() {
        return isObserved;
    }

    /**
     *
     * @param isObserved
     * The is_observed
     */
    @JsonProperty("is_observed")
    public void setIsObserved(Object isObserved) {
        this.isObserved = isObserved;
    }

    /**
     *
     * @return
     * The isBlocked
     */
    @JsonProperty("is_blocked")
    public Object getIsBlocked() {
        return isBlocked;
    }

    /**
     *
     * @param isBlocked
     * The is_blocked
     */
    @JsonProperty("is_blocked")
    public void setIsBlocked(Object isBlocked) {
        this.isBlocked = isBlocked;
    }

    /**
     *
     * @return
     * The sex
     */
    @JsonProperty("sex")
    public String getSex() {
        return sex;
    }

    /**
     *
     * @param sex
     * The sex
     */
    @JsonProperty("sex")
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     *
     * @return
     * The url
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The violationUrl
     */
    @JsonProperty("violation_url")
    public Object getViolationUrl() {
        return violationUrl;
    }

    /**
     *
     * @param violationUrl
     * The violation_url
     */
    @JsonProperty("violation_url")
    public void setViolationUrl(Object violationUrl) {
        this.violationUrl = violationUrl;
    }

    /**
     *
     * @return
     * The userkey
     */
    @JsonProperty("userkey")
    public String getUserkey() {
        return userkey;
    }

    /**
     *
     * @param userkey
     * The userkey
     */
    @JsonProperty("userkey")
    public void setUserkey(String userkey) {
        this.userkey = userkey;
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

