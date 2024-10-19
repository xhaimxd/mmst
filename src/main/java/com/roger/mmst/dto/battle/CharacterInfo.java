package com.roger.mmst.dto.battle;

import cn.hutool.core.util.RandomUtil;
import com.roger.mmst.constants.job.Job;
import com.roger.mmst.entity.item.ItemEquipWeapon;
import com.roger.mmst.valueobject.battle.SkillInfo;
import com.roger.mmst.valueobject.character.Character;
import com.roger.mmst.world.Life;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import static com.roger.mmst.util.DamageCalculator.HUNDRED;

@Data
public class CharacterInfo implements Life, Character {
    private Long hp = 100L;
    private Integer strVal;
    private Integer dexVal;
    private Integer intVal;
    private Integer lukVal;
    private ItemEquipWeapon weapon;
    private Double finalDamage;
    private Job job;
    private String uniqueId;
    private SkillInfo mainAttackSkill;

    @Override
    public String getName() {
        return "YazawaNiiico";
    }

    private Long getDamageRange() {
        return getActualDamage()
                .multiply(
                        BigDecimal.ONE.add(BigDecimal.valueOf(getDamage()).divide(HUNDRED, RoundingMode.HALF_EVEN))
                ).multiply(
                        BigDecimal.ONE.add(BigDecimal.valueOf(getFinalDamage()).divide(HUNDRED, RoundingMode.HALF_EVEN))
                ).longValue();
    }

    @Override
    public Long getShowDamage() {
        return BigDecimal.valueOf(RandomUtil.randomDouble(getLowerDamageRange(), 1))
                .multiply(BigDecimal.valueOf(getDamageRange())).longValue();
    }

    public Double getLowerDamageRange() {
        return Math.max(20d, getMastery() / 100);
    }

    private Double getMastery() {
        return 0d;
    }

    private BigDecimal getActualDamage() {
        if (weapon == null) return BigDecimal.ZERO;
        return BigDecimal.valueOf(weapon.getWeaponType().getMultiplier())
                .multiply(BigDecimal.valueOf(getStatDamage()))
                .multiply(BigDecimal.valueOf(getTotalAtt()).divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN));
    }

    private Long getStatDamage() {
        return job.getPrimaryStat().applyAsInt(this) * 4L + job.getSecondStat().applyAsInt(this);
    }

    private Long getTotalAtt() {
        return 1L;
    }

    @Override
    public Double getDamage() {
        return 0.0;
    }

    @Override
    public Integer getDefense() {
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
    public <E extends Life> void attack(List<E> lives, List<String> messages, Integer column, Integer row) {
        List<E> livesMonster = lives.stream().filter(life -> !life.isDead()).collect(Collectors.toList());
        List<E> es = RandomUtil.randomEleList(livesMonster, 3);
        es.forEach(life -> {
            life.setHp(life.getHp() - 50);
            messages.add("【%s】使用【群体攻击】对【%s】造成了【%d】点伤害".formatted(getName(), life.getName(), 50));
        });
    }
}
