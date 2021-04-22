package com.etell.toxictalks.dto.ws;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class WsEventDto<T> {
    private WsObjectType wsObjectType;
    private WsEventType wsEventType;

    private T body;

    public WsEventDto(WsObjectType wsObjectType, WsEventType wsEventType, T body) {
        this.wsObjectType = wsObjectType;
        this.wsEventType = wsEventType;
        this.body = body;
    }

    public WsObjectType getWsObjectType() {
        return wsObjectType;
    }

    public void setWsObjectType(WsObjectType wsObjectType) {
        this.wsObjectType = wsObjectType;
    }

    public WsEventType getWsEventType() {
        return wsEventType;
    }

    public void setWsEventType(WsEventType wsEventType) {
        this.wsEventType = wsEventType;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
