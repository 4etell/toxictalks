package com.etell.toxictalks.domain;

import com.etell.toxictalks.projections.domain.ChatProjectionDomain;
import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chat")
public class Chat extends BaseEntity implements ChatProjectionDomain {

    @NotNull
    private String name;

    @NotNull
    private Boolean attitude;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private User participant;


    public Chat() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Boolean getAttitude() {
        return attitude;
    }

    public void setAttitude(Boolean attitude) {
        this.attitude = attitude;
    }

    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    @Override
    public Long getCreator_id() {
        if (getCreator() != null) {
            return getCreator().getId();
        } else return null;
    }

    @Override
    public Long getParticipant_id() {
        if (getParticipant() != null) {
            return getParticipant().getId();
        } else return null;
    }
}
