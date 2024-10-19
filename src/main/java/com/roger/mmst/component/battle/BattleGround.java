package com.roger.mmst.component.battle;

import com.roger.mmst.dto.battle.CharacterInfo;
import com.roger.mmst.entity.life.Monster;

import java.util.Map;

/**
 * @author Roger Liu
 * @date 2024/03/21
 */
public interface BattleGround {
    void init(String sessionId, CharacterInfo character, int column, int row, Long mapId);
}
