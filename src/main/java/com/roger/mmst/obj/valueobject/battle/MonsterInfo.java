package com.roger.mmst.obj.valueobject.battle;

import cn.hutool.core.util.NumberUtil;
import com.roger.mmst.obj.world.Life;
import lombok.Data;

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.List;

@Data
public class MonsterInfo implements Serializable, Life {
    private String uniqueId;
    private Boolean exist = true;
    private Long mobId = 100000L;
    private String name = "绿蜗牛";
    private Double hpPercent;
    private Long maxHp = 100L;
    private Long hp = 100L;

    public Double getHpPercent() {
        return NumberUtil.div(hp, maxHp).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static MonsterInfo empty() {
        MonsterInfo res = new MonsterInfo();
        res.exist = false;
        return res;
    }

    @Override
    public Integer getLevel() {
        return 255;
    }

    @Override
    public Long getShowDamage() {
        return 0L;
    }

    @Override
    public Double getDamage() {
        return 0d;
    }

    @Override
    public Integer getDefense() {
        return 0;
    }

    @Override
    public double getAttackFrequency() {
        return 2;
    }

    public boolean isDead() {
        return hp <= 0L || !exist;
    }

    @Override
    public <E extends Life> void attack(List<E> lives, List<String> messages, Integer column, Integer row) {
        for (Life life : lives) {
            life.setHp(life.getHp() - 0);
            messages.add("【%s】使用【普通攻击】对【%s】造成了【%d】点伤害".formatted(name, life.getName(), 0));
        }
    }
}
