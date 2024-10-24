package com.roger.mmst.component.battle;

import com.roger.mmst.obj.dto.battle.CharacterInfo;

/**
 * @author Roger Liu
 * @date 2024/03/21
 */
public interface BattleGround {
    void init(String sessionId, CharacterInfo character, int column, int row, Long mapId);
}
