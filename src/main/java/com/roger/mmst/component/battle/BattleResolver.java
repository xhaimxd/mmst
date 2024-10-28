package com.roger.mmst.component.battle;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.roger.mmst.constants.exception.WsNotifyException;
import com.roger.mmst.domain.entity.character.Character;
import com.roger.mmst.obj.dto.battle.BattleBeginDTO;
import com.roger.mmst.obj.valueobject.EntityTransfer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class BattleResolver {
    @Resource
    private ObjectProvider<BattleGround> battleGroundProvider;

    private final Map<Long, AtomicBoolean> battleFutures = new ConcurrentHashMap<>();

    private final TimedCache<Long, Boolean> timedCache = CacheUtil.newTimedCache(10000L);

    private final ReentrantLock reentrantLock = new ReentrantLock(true);

    public void battleBegin(Long userId, String sessionId, Character character, BattleBeginDTO message) {
        try {
            if (reentrantLock.tryLock(3, TimeUnit.SECONDS)) {
                verify(userId);
                BattleGround battleGround = battleGroundProvider.getObject();
                AtomicBoolean running = new AtomicBoolean(true);
                try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
                    executorService.submit(() -> battleGround.init(sessionId, EntityTransfer.toCharacterInfo(character), 2, 2, message.getMapId(), running));
                    battleFutures.put(userId, running);
                    timedCache.put(userId, true);
                }
            } else {
                throw new WsNotifyException("系统异常，请重试");
            }
        } catch (InterruptedException e) {
            throw new WsNotifyException("系统异常，请重试");
        } finally {
            if (reentrantLock.isHeldByCurrentThread()) reentrantLock.unlock();
        }
    }

    public void battleStop(Long userId) {
        AtomicBoolean runing = battleFutures.get(userId);
        if (runing != null) {
            runing.set(false);
            battleFutures.remove(userId);
        }
    }

    private void verify(Long userId) {
        if (battleFutures.containsKey(userId)) {
            throw new WsNotifyException("战斗中");
        }
        if (timedCache.containsKey(userId)) {
            throw new WsNotifyException("10秒内不可重复发起战斗");
        }
    }
}
