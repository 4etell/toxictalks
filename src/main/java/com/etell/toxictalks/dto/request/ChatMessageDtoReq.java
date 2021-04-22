package com.etell.toxictalks.dto.request;

public class ChatMessageDtoReq {

    private String text;

    private Long chatId;

    public ChatMessageDtoReq () {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
