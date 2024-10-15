package com.roger.mmst.valueobject.battle;

import com.roger.mmst.dto.battle.CharacterInfo;
import lombok.Data;

import java.util.List;

@Data
public class BattleInfo {
    private List<String> messages;
    private List<MonsterInfo> monsters;
    private CharacterInfo character;
}
