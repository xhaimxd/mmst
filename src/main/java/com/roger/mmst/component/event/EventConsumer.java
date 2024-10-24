package com.roger.mmst.component.event;

import com.roger.mmst.component.battle.BattleRewardCalculator;
import com.roger.mmst.obj.dto.battle.CharacterInfo;
import com.roger.mmst.obj.valueobject.battle.BattleRewardInfo;
import com.roger.mmst.obj.valueobject.battle.MonsterInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EventConsumer {

    @Resource
    private BattleRewardCalculator battleRewardCalculator;

    @EventListener
    public void battleReward(BattleRewardInfo info) {
        List<String> messages = info.getMessages();
        CharacterInfo character = info.getCharacter();
        MonsterInfo monster = info.getMonster();
        messages.add("【%s】杀死了【%s】，获得【%d】点经验".formatted(character.getName(), monster.getName(), 3));
        Long meso = battleRewardCalculator.getMeso(character, monster);
        messages.add("【%s】杀死了【%s】，获得【%d】金币".formatted(character.getName(), monster.getName(), meso));
    }
}
