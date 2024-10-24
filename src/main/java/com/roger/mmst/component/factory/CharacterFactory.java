package com.roger.mmst.component.factory;

import com.roger.mmst.component.strategy.stat.CharacterBaseStatContext;
import com.roger.mmst.constants.job.Job;
import com.roger.mmst.domain.entity.character.Character;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class CharacterFactory {

    @Resource
    private CharacterBaseStatContext characterBaseStatContext;

    public Character create(String name, Job job) {
        Character character = new Character();
        character.setName(name);
        character.setJob(job);
        characterBaseStatContext.baseStats(character);
        return character;
    }
}
