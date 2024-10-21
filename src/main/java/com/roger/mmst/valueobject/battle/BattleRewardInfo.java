package com.roger.mmst.valueobject.battle;

import com.roger.mmst.dto.battle.CharacterInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BattleRewardInfo {
    private CharacterInfo character;
    private MonsterInfo monster;
    private List<String> messages;
}
