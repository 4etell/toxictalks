package com.etell.toxictalks.dto.response;

import com.etell.toxictalks.domain.Status;
import com.etell.toxictalks.dto.ChatDto;

import java.util.List;

public class UserChatDtoRes extends ChatDto {

    private Status status;

    private List<ChatMessageDtoRes> messages;

    public UserChatDtoRes() {

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ChatMessageDtoRes> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessageDtoRes> messages) {
        this.messages = messages;
    }
}
