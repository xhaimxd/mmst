package com.roger.mmst.valueobject.battle;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BattleRewardInfo {
    private Long characterId;
    private Long mobId;

    public static BattleRewardInfo of(Long characterId, Long mobId) {
        return new BattleRewardInfo(characterId, mobId);
    }
}
