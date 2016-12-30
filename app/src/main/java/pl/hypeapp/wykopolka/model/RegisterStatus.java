package pl.hypeapp.wykopolka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterStatus {

    @JsonProperty("Status")
    private boolean registerStatus;

    @JsonProperty("Status")
    public boolean getRegisterStatus() {
        return registerStatus;
    }

    @JsonProperty("Status")
    public void setRegisterStatus(boolean registerStatus) {
        this.registerStatus = registerStatus;
    }

}
