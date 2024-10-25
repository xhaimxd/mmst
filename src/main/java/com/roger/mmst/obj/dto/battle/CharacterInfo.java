package com.roger.mmst.obj.dto.battle;

import cn.hutool.core.util.RandomUtil;
import com.roger.mmst.constants.job.Job;
import com.roger.mmst.domain.entity.item.ItemEquipWeapon;
import com.roger.mmst.util.DamageCalculator;
import com.roger.mmst.obj.valueobject.battle.SkillInfo;
import com.roger.mmst.obj.valueobject.character.Character;
import com.roger.mmst.obj.world.Life;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.roger.mmst.util.DamageCalculator.HUNDRED;

@Data
public class CharacterInfo implements Life, Character {
    private String name;
    private Integer level;
    private Long hp = 100L;
    private Integer strVal = 49;
    private Integer dexVal = 4;
    private Integer intVal = 4;
    private Integer lukVal = 4;
    private ItemEquipWeapon weapon;
    private Double finalDamage = 0d;
    private Job job = Job.BEGINNER;
    private String uniqueId;
    private SkillInfo mainAttackSkill;
    private Double criticalRate = 20d;
    private Integer criticalDamage = 0;

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
        return Math.max(1, BigDecimal.valueOf(RandomUtil.randomDouble(getLowerDamageRange(), 1))
                .multiply(BigDecimal.valueOf(getDamageRange())).longValue());
    }

    public Double getLowerDamageRange() {
        return Math.max(20d, getMastery()) / 100;
    }

    private Double getMastery() {
        return 0d;
    }

    private BigDecimal getActualDamage() {
        if (weapon == null) return BigDecimal.ZERO;
        return BigDecimal.valueOf(weapon.getWeaponType().getMultiplier())
                .multiply(BigDecimal.valueOf(getStatDamage()))
                .multiply(BigDecimal.valueOf(getTotalAtt()))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
    }

    private Long getStatDamage() {
        return job.getPrimaryStat().applyAsInt(this) * 4L + job.getSecondStat().applyAsInt(this);
    }

    private Long getTotalAtt() {
        return Long.valueOf(getWeapon().getAtt());
    }

    public Integer getMesoRate() {
        return 100;
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
        return 0.5;
    }

    @Override
    public boolean isDead() {
        return hp <= 0;
    }

    @Override
    public Double getSkillDamage() {
        return mainAttackSkill == null ? 100d : mainAttackSkill.getDamage();
    }

    @Override
    public <E extends Life> void attack(List<E> lives, List<String> messages, Integer column, Integer row) {
        List<E> targets = mainAttackSkill.getTarget(lives, column, row);
        targets.forEach(target -> {
            for (int i = 0; i < mainAttackSkill.getAttackCount(); i++) {
                Long damage = DamageCalculator.getDamage(this, target);
                target.setHp(target.getHp() - damage);
                messages.add("【%s】使用【%s】对【%s】造成了【%d】点伤害".formatted(getName(), mainAttackSkill.getName(), target.getName(), damage));
            }
        });
    }
}
