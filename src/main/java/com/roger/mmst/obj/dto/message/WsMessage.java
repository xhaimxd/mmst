package com.roger.mmst.obj.dto.message;

public interface WsMessage {

    Type getType();

    public enum Type {
        LAYER, BATTLE,
    }
}
