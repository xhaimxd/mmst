package com.roger.mmst.obj.dto.message;

import com.roger.mmst.obj.dto.battle.CharacterInfo;
import com.roger.mmst.obj.valueobject.battle.MonsterInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BattleMessage implements WsMessage {
    private Type type;
    private List<String> message;
    private List<MonsterInfo> monsterInfos;
    private CharacterInfo characterInfo;
    private Integer row;
    private Integer column;

    public static BattleMessage of(List<String> message, List<MonsterInfo> monsterInfos, CharacterInfo characterInfo, Integer row, Integer column) {
        return new BattleMessage(Type.BATTLE, message, monsterInfos, characterInfo, row, column);
    }
}
