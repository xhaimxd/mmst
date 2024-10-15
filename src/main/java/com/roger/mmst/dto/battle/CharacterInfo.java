package com.roger.mmst.dto.battle;

import cn.hutool.core.util.RandomUtil;
import com.roger.mmst.valueobject.battle.SkillInfo;
import com.roger.mmst.world.Life;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CharacterInfo implements Life {
    private Long hp = 100L;
    private String uniqueId;
    private SkillInfo mainAttackSkill;

    @Override
    public String getName() {
        return "YazawaNiiico";
    }

    @Override
    public Integer getDefense(Life attacker) {
        return 0;
    }

    @Override
    public double getAttackFrequency() {
        return 2;
    }

    @Override
    public boolean isDead() {
        return hp <= 0;
    }

    @Override
    public <E extends Life> void attack(List<E> lives, List<String> messages) {
        List<E> livesMonster = lives.stream().filter(life -> !life.isDead()).collect(Collectors.toList());
        List<E> es = RandomUtil.randomEleList(livesMonster, 3);
        es.forEach(life -> {
            life.setHp(life.getHp() - 50);
            messages.add("【%s】使用【群体攻击】对【%s】造成了【%d】点伤害".formatted(getName(), life.getName(), 50));
        });
    }
}
