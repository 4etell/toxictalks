package com.etell.toxictalks.projections;

import com.etell.toxictalks.domain.Status;

public interface ChatProjection {

    Long getId();

    String getName();

    Long getCreator_id();

    Long getParticipant_id();

    Boolean getAttitude();

    Status getStatus();

}
