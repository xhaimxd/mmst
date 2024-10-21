package com.roger.mmst.component.battle;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.roger.mmst.component.message.WsMessagePublisher;
import com.roger.mmst.dto.battle.CharacterInfo;
import com.roger.mmst.valueobject.battle.BattleRewardInfo;
import com.roger.mmst.valueobject.battle.MonsterInfo;
import com.roger.mmst.dto.message.BattleMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class DefaultBattleGround implements BattleGround, DisposableBean {

    public static final Integer MAX_MONSTER = 7 * 7;

    @Resource
    private MonsterSpawner monsterSpawner;
    @Resource
    private WsMessagePublisher wsMessagePublisher;
    @Resource
    private ApplicationEventPublisher eventPublisher;
    private double spawnFrequency = 1;
    private Integer column;
    private Integer row;
    private List<MonsterInfo> monsters;
    private List<String> messages;
    private Long mapId;
    private boolean[] existStates;
    private ScheduledExecutorService spawnScheduler;
    private ScheduledExecutorService characterScheduler;
    private ScheduledExecutorService monsterScheduler;
    private boolean initialized = false;
    private int spawnCount = 2 + 1; //一次最多生成2只
    private String sessionId;
    private CharacterInfo character;
    private Map<String, ScheduledFuture<?>> monsterFutures;
    private volatile boolean running = true;

    public void init(String sessionId, CharacterInfo character, int column, int row, Long mapId) {
        if (!initialized) {
            log.info("初始化战斗场：{}", Thread.currentThread().getName());
            this.mapId = mapId;
            this.sessionId = sessionId;
            this.character = character;
            this.monsters = new CopyOnWriteArrayList<>();
            this.messages = new CopyOnWriteArrayList<>();
            this.column = column;
            this.row = row;
            int maxMonsters = column * row;
            this.existStates = new boolean[maxMonsters];
            for (int i = 0; i < maxMonsters; i++) {
                monsters.add(MonsterSpawner.EMPTY_MONSTER);
            }
            spawnScheduler = ThreadUtil.createScheduledExecutor(1);
            characterScheduler = ThreadUtil.createScheduledExecutor(1);
            monsterScheduler = ThreadUtil.createScheduledExecutor(MAX_MONSTER);
            monsterFutures = new ConcurrentHashMap<>();
            spawnScheduler.scheduleAtFixedRate(this::spawn, 0, (long) (spawnFrequency * 1000L), TimeUnit.MILLISECONDS);
            characterScheduler.scheduleAtFixedRate(this::characterAttack, 0, (long) (character.getAttackFrequency() * 1000L), TimeUnit.MILLISECONDS);
            initialized = true;
            while (running) {
            }
            ThreadUtil.safeSleep(1000L);
        }
    }

    private void message() {
        if (!messages.isEmpty()) {
            List<String> sendMessage = new ArrayList<>(messages);
            messages.clear();
            log.info("\r\n{}", String.join("\r\n", sendMessage));
            wsMessagePublisher.publish(sessionId, BattleMessage.of(sendMessage));
            if (log.isDebugEnabled()) {
                String monsterInfo = CollUtil.split(monsters, this.row)
                        .stream()
                        .map(monsterCol -> monsterCol.stream()
                                .map(monster -> monster.isDead() ? "[  ]" : String.valueOf(monster.getHp()))
                                .collect(Collectors.joining("   ")))
                        .collect(Collectors.joining("\r\n"));
                log.debug("目前怪物阵型及血量：\r\n {}", monsterInfo);
            }
        }
    }

    private void exist(int index, boolean exist) {
        synchronized (this.existStates) {
            existStates[index] = exist;
        }
    }

    private void spawn() {
        try {
            List<Integer> emptyIndexes = new ArrayList<>();
            for (int i = 0; i < existStates.length; i++) {
                if (!existStates[i]) {
                    emptyIndexes.add(i);
                }
            }
            if (!emptyIndexes.isEmpty()) {
                int count = RandomUtil.randomInt(1, spawnCount);
                List<Integer> randomIndices = RandomUtil.randomEleList(emptyIndexes, count);
                randomIndices.forEach(index -> {
                    MonsterInfo spawn = monsterSpawner.spawn(this.mapId);
                    MonsterInfo clone = ObjectUtil.clone(spawn);
                    clone.setUniqueId(IdUtil.fastSimpleUUID());
                    monsters.set(index, clone);
                    exist(index, true);
                    monsterFutures.put(clone.getUniqueId(), monsterScheduler.scheduleAtFixedRate(() -> this.monsterAttach(clone), (long) (clone.getAttackFrequency() * 1000L), (long) (clone.getAttackFrequency() * 1000L), TimeUnit.MILLISECONDS));
                    messages.add(String.format("%s出现了！", clone.getName()));
                });
                message();
            }
        } catch (Exception e) {
            log.error("生成怪物出现了异常", e);
        }
    }

    private void characterAttack() {
        try {
            character.attack(monsters, messages, column, row);
            checkMonsters();
            message();
        } catch (Exception e) {
            log.error("角色攻击出现了异常", e);
        }
    }

    private void monsterAttach(MonsterInfo monster) {
        try {
            monster.attack(List.of(this.character), messages, column, row);
            checkCharacter();
            message();
        } catch (Exception e) {
            log.info("怪物攻击出现了异常", e);
        }
    }

    private void checkCharacter() {
        if (running && this.character.isDead()) {
            this.running = false;
            messages.add("【%s】被怪物杀死了！".formatted(this.character.getName()));
            spawnScheduler.shutdown();
            characterScheduler.shutdown();
            monsterScheduler.shutdown();
        }
    }

    private void checkMonsters() {
        for (int index = 0; index < monsters.size(); index++) {
            MonsterInfo monster = monsters.get(index);
            if (Boolean.TRUE.equals(monster.getExist()) && monster.isDead()) {
                ScheduledFuture<?> future = monsterFutures.get(monster.getUniqueId());
                monsterFutures.remove(monster.getUniqueId());
                future.cancel(true);
                this.exist(index, false);
                eventPublisher.publishEvent(new BattleRewardInfo(this.character, monster, messages));
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        monsters = null;
    }
}
