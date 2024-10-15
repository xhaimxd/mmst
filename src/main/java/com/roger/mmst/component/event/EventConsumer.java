package com.roger.mmst.component.event;

import com.roger.mmst.valueobject.battle.BattleRewardInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventConsumer {

    @EventListener
    public void battleReward(BattleRewardInfo info) {
        log.info("战斗奖励：{}", info);
    }
}
