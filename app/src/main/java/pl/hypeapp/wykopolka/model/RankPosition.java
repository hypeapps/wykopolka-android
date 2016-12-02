package pl.hypeapp.wykopolka.model;

public class RankPosition {
    public String nickname;
    public String position;

    public void setPosition(String position) {
        this.position = position;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPosition() {
        return position + ".";
    }

    public String getNickname() {
        return "@" + nickname;
    }
}
