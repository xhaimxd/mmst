package com.roger.mmst.component.strategy.stat;

import com.roger.mmst.constants.exception.VerifyException;
import com.roger.mmst.constants.job.Job;
import com.roger.mmst.domain.entity.character.Character;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CharacterBaseStatContext {

    @Resource
    private List<CharacterBaseStatStrategy> statStrategies;

    private static final Map<Job, CharacterBaseStatStrategy> JOB_STRATEGY = new HashMap<>();

    @PostConstruct
    public void init() {
        statStrategies.forEach(strategy -> JOB_STRATEGY.put(strategy.getHandleJob(), strategy));
    }

    public void baseStats(Character character) {
        CharacterBaseStatStrategy strategy = JOB_STRATEGY.get(character.getJob());
        if (strategy == null) {
            throw new VerifyException("非法职业：" + character.getJob());
        }
        strategy.setBaseStat(character);
    }
}
