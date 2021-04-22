package com.etell.toxictalks.domain;

import com.etell.toxictalks.projections.domain.ChatMessageProjectionDomain;
import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
public class ChatMessage extends BaseEntity implements ChatMessageProjectionDomain {

    @NotNull
    private String text;

    @NotNull
    private Boolean read = false;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @NotNull
    private User author;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    @NotNull
    private Chat chat;

    public ChatMessage() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public Long getAuthor_id() {
        return getAuthor().getId();
    }

    @Override
    public LocalDateTime getUpdate_date() {
        return getUpdateDate();
    }

    @Override
    public Long getChat_id() {
        return getChat().getId();
    }
}
