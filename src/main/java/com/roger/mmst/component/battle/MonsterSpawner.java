package com.roger.mmst.component.battle;

import cn.hutool.core.util.ObjectUtil;
import com.roger.mmst.valueobject.battle.MonsterInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class MonsterSpawner {

    public static MonsterInfo EMPTY_MONSTER = MonsterInfo.empty();

    @Resource
    private CacheRepository cacheRepository;

    public MonsterInfo spawn(Long mapId) {
        MonsterInfo monsterInfo = cacheRepository.getMonsterInfo(mapId);
        return ObjectUtil.clone(monsterInfo);
    }

}
