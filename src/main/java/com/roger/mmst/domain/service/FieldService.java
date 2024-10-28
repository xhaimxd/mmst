package com.roger.mmst.domain.service;

import com.roger.mmst.component.battle.BattleResolver;
import com.roger.mmst.domain.entity.character.Character;
import com.roger.mmst.obj.dto.battle.BattleBeginDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class FieldService {

    @Resource
    private CharacterService characterService;
    @Resource
    private BattleResolver battleResolver;

    public void battleBegin(Long userId, String sessionId, BattleBeginDTO message) {
        Character character = characterService.findById(message.getCharacterId()).orElseThrow(() -> new RuntimeException("角色不存在"));
        battleResolver.battleBegin(userId, sessionId, character, message);
    }

    public void battleStop(Long userId) {
        battleResolver.battleStop(userId);
    }
}
