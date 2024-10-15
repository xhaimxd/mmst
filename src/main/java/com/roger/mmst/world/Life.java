package com.roger.mmst.world;

import java.util.List;

/**
 * @author Roger Liu
 * @date 2024/03/07
 */
public interface Life {

    String getUniqueId();

    String getName();

    Integer getDefense(Life attacker);

    double getAttackFrequency();

    void setHp(Long hp);

    Long getHp();

    boolean isDead();

    <E extends Life> void attack(List<E> lives, List<String> messages);
}
