package com.roger.mmst.valueobject.battle;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.roger.mmst.world.Life;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SkillInfo {
    private Long id;
    private String name;
    //攻击怪物数量
    private Integer attackNumber;
    private AttackType attackType;
    //攻击次数
    private Integer attackCount;
    //伤害
    private Double damage;
    private DamageType damageType;

    public enum AttackType {
        FRONT, //前排
        LINE, //横排
        ALL //全部
    }

    public enum DamageType {
        FIX, //固定伤害
        PERCENT //倍率伤害
    }

    public <E extends Life> List<E> getTarget(List<E> defenders, int column, int row) {
        return switch (attackType) {
            case ALL -> {
                List<E> lives = defenders.stream().filter(life -> !life.isDead()).toList();
                yield RandomUtil.randomEleList(lives, attackNumber);
            }
            case LINE -> {
                int randomColumn = RandomUtil.randomInt(0, column);
                List<E> lineLives = CollUtil.sub(defenders, randomColumn * row, (randomColumn + 1));
                yield RandomUtil.randomEleList(lineLives, attackNumber);
            }
            case FRONT -> {
                int num = attackNumber;
                List<E> res = new ArrayList<>();
                for (int col = 0; col < column && num > 0; col++) {
                    List<E> selected = new ArrayList<>();
                    for (int r = 0; r < row; r++) {
                        int index = col * row + r;
                        E live = defenders.get(index);
                        if (!live.isDead()) {
                            selected.add(live);
                        }
                    }
                    if (selected.size() <= num) {
                        res.addAll(selected);
                    } else {
                        selected = RandomUtil.randomEleList(selected, num);
                        res.addAll(selected);
                    }
                    num -= selected.size();
                }
                yield res;
            }
        };
    }
}
