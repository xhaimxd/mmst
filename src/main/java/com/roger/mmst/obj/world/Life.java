package com.roger.mmst.obj.world;

import java.util.List;

/**
 * @author Roger Liu
 * @date 2024/03/07
 */
public interface Life {

    String getUniqueId();

    Integer getLevel();

    String getName();

    Long getShowDamage();

    Double getDamage();

    default Integer getBossDamage() {
        return 0;
    }

    default Integer getNormalDamage() {
        return 0;
    }

    default Integer getCriticalDamage() {
        return -1;
    }

    default Double getDefenseIgnore() {
        return 100.0;
    }

    Integer getDefense();

    default Double getCriticalRate() {
        return 0d;
    }

    default Double getSkillDamage() {
        return 100d;
    }

    double getAttackFrequency();

    void setHp(Long hp);

    Long getHp();

    boolean isDead();

    default boolean isBoss() {
        return false;
    }

    <E extends Life> void attack(List<E> lives, List<String> messages, Integer column, Integer row);
}
