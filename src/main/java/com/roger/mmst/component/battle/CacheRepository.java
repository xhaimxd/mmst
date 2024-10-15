package com.roger.mmst.component.battle;

import com.roger.mmst.valueobject.MonsterSpawnInfo;
import com.roger.mmst.valueobject.battle.MonsterInfo;

import java.util.List;

public interface CacheRepository {

    List<MonsterSpawnInfo> getMonsterSpawnInfoByMapId(Long mapId);

    MonsterInfo getMonsterInfo(Long mapId);
}
