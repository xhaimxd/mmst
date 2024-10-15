package com.roger.mmst.dto.message;

public interface WsMessage {

    Type getType();

    public enum Type {
        LAYER, BATTLE,
    }
}
