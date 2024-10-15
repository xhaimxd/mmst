package com.roger.mmst.valueobject.battle;

import com.roger.mmst.world.Life;
import lombok.Data;

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

    enum AttackType {
        FRONT, //前排
        LINE, //横排
        ALL //全部
    }

    enum DamageType {
        FIX, //固定伤害
        PERCENT //倍率伤害
    }

    public void calculateDamage(Life attacker, Life target) {

    }
}
