package com.etell.toxictalks.projections;

import java.time.LocalDateTime;

public interface ChatMessageProjection {

    Long getId();

    String getText();

    Long getAuthor_id();

    LocalDateTime getUpdate_date();

    Long getChat_id();

    Boolean getRead();
    
}
