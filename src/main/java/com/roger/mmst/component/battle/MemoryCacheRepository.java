package com.roger.mmst.component.battle;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LFUCache;
import cn.hutool.core.util.RandomUtil;
import com.roger.mmst.obj.valueobject.MonsterSpawnInfo;
import com.roger.mmst.obj.valueobject.battle.MonsterInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class MemoryCacheRepository implements CacheRepository {

    private static final ConcurrentMap<Long, List<MonsterSpawnInfo>> MONSTER_SPAWN_MAP = new ConcurrentHashMap<>();

    private static final LFUCache<Long, MonsterInfo> MONSTER_CACHE = CacheUtil.newLFUCache(100);

    @Override
    public List<MonsterSpawnInfo> getMonsterSpawnInfoByMapId(Long mapId) {
        List<MonsterSpawnInfo> spawnInfos = MONSTER_SPAWN_MAP.computeIfAbsent(mapId, k -> {
            return List.of(new MonsterSpawnInfo());
        });
        return spawnInfos;
    }

    @Override
    public MonsterInfo getMonsterInfo(Long mapId) {
        Long mobId = RandomUtil.randomEle(getMonsterSpawnInfoByMapId(mapId)).getId();
        return MONSTER_CACHE.get(mobId, () -> {
            return new MonsterInfo();
        });
    }
}
