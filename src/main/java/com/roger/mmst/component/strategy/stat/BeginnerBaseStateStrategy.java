package com.roger.mmst.component.strategy.stat;

import com.roger.mmst.constants.job.Job;
import com.roger.mmst.domain.entity.character.Character;
import org.springframework.stereotype.Component;

@Component
public class BeginnerBaseStateStrategy implements CharacterBaseStatStrategy {
    @Override
    public Job getHandleJob() {
        return Job.BEGINNER;
    }

    @Override
    public void setBaseStat(Character character) {
        character.setBaseStr(10);
        character.setBaseDex(4);
        character.setBaseLuk(4);
        character.setBaseInt(4);
    }
}
