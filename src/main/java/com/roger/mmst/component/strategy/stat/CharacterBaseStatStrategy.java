package com.roger.mmst.component.strategy.stat;

import com.roger.mmst.constants.job.Job;
import com.roger.mmst.domain.entity.character.Character;

public interface CharacterBaseStatStrategy {

    Job getHandleJob();

    void setBaseStat(Character character);
}
