package com.etell.toxictalks.dto.request;

public class ProfileDtoReq {

    private String email;

    private String password;

    public ProfileDtoReq () {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
