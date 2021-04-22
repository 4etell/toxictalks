package com.etell.toxictalks.dto.response;

import com.etell.toxictalks.domain.Status;

public class UserDtoRes {

    private Long id;

    private Status status;

    private String username;

    private String email;

    public UserDtoRes() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
