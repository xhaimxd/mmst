package com.roger.mmst.domain.service;

import cn.hutool.core.thread.ThreadUtil;
import com.roger.mmst.component.battle.BattleGround;
import com.roger.mmst.domain.entity.character.Character;
import com.roger.mmst.obj.dto.battle.BattleBeginDTO;
import com.roger.mmst.obj.valueobject.EntityTransfer;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class FieldService {

    @Resource
    private ObjectProvider<BattleGround> battleGroundProvider;
    @Resource
    private CharacterService characterService;

    public void battleBegin(String sessionId, BattleBeginDTO message) {
        Character character = characterService.findById(message.getCharacterId()).orElseThrow(() -> new RuntimeException("角色不存在"));
        BattleGround battleGround = battleGroundProvider.getObject();
        ThreadUtil.execAsync(() -> {
            battleGround.init(sessionId, EntityTransfer.toCharacterInfo(character), 2, 2, message.getMapId());
            ThreadUtil.safeSleep(1000L);
        });
    }
}
