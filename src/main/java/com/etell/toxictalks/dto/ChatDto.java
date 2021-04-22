package com.etell.toxictalks.dto;

public class ChatDto {

    private Long id;

    private Long creatorId;

    private Long participantId;

    private String name;

    private Boolean attitude;

    public ChatDto () {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAttitude() {
        return attitude;
    }

    public void setAttitude(Boolean attitude) {
        this.attitude = attitude;
    }
}
