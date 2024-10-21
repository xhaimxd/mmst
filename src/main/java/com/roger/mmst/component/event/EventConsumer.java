package com.roger.mmst.component.event;

import com.roger.mmst.dto.battle.CharacterInfo;
import com.roger.mmst.valueobject.battle.BattleRewardInfo;
import com.roger.mmst.valueobject.battle.MonsterInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EventConsumer {

    @EventListener
    public void battleReward(BattleRewardInfo info) {
        List<String> messages = info.getMessages();
        CharacterInfo character = info.getCharacter();
        MonsterInfo monster = info.getMonster();
        messages.add("【%s】杀死了【%s】，获得【%d】点经验".formatted(character.getName(), monster.getName(), 3));
    }
}
