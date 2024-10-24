package com.roger.mmst.obj.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BattleMessage implements WsMessage {
    private Type type;
    private List<String> message;

    public static BattleMessage of(List<String> message) {
        return new BattleMessage(Type.BATTLE, message);
    }
}
