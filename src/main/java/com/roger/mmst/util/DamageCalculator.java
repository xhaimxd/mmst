package com.roger.mmst.util;

import cn.hutool.core.util.RandomUtil;
import com.roger.mmst.world.Life;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class DamageCalculator {

    public static final BigDecimal ONE = BigDecimal.ONE;
    public static final BigDecimal ZERO = BigDecimal.ZERO;
    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private DamageCalculator() {

    }

    public static Long getDamage(Life attacker, Life defender) {
        BigDecimal hitDamage = getHitDamage(attacker, defender.isBoss());
        log.debug("打击原始伤害：{}", hitDamage);
        if (RandomUtil.randomDouble(0, 1) < attacker.getCriticalRate()) {
            BigDecimal criticalDamageMultiplier = getCriticalDamageMultiplier(attacker);
            log.debug("打出了暴击，伤害倍率：{}", criticalDamageMultiplier);
            hitDamage = hitDamage.multiply(criticalDamageMultiplier);
        }
        if (defender.isBoss()) {
            BigDecimal defMultiplier = getDefMultiplier(attacker, defender);
            log.debug("打boss，无视伤害倍率：{}", defMultiplier);
            hitDamage = hitDamage.multiply(defMultiplier);
        }
        long res = Math.max(1, hitDamage.setScale(0, RoundingMode.HALF_EVEN).longValue());
        log.debug("最终造成伤害：{}", res);
        return res;
    }

    public static BigDecimal getCriticalDamageMultiplier(Life attacker) {
        if (attacker.getCriticalDamage() == -1) return ZERO;
        return ONE.add(
                BigDecimal.valueOf(RandomUtil.randomInt(20, 51) + attacker.getCriticalDamage())
                        .divide(HUNDRED)
        );
    }

    public static BigDecimal getDefMultiplier(Life attacker, Life defender) {
        return ONE.subtract(
                BigDecimal.valueOf(defender.getDefense()).divide(HUNDRED)
                        .multiply(ONE.subtract(
                                BigDecimal.valueOf(attacker.getDefenseIgnore()).divide(HUNDRED)
                        ))
        );
    }

    public static BigDecimal getHitDamage(Life attacker, boolean isBoss) {
        BigDecimal showDamage = getShowDamage(attacker);
        log.debug("面板伤害：{}", showDamage);
        BigDecimal totalDamage = getTotalDamage(attacker);
        log.debug("总伤害：{}", totalDamage);
        BigDecimal skillDamage = BigDecimal.valueOf(attacker.getSkillDamage());
        log.debug("技能伤害：{}", skillDamage);
        BigDecimal specialDamage = getSpecialDamage(attacker, isBoss);
        log.debug("小怪/boss伤害：{}", specialDamage);
        return showDamage.divide(ONE.add(totalDamage.divide(HUNDRED)))
                .multiply(skillDamage.divide(HUNDRED))
                .multiply(ONE.add(totalDamage.add(specialDamage).divide(HUNDRED)));
    }

    public static BigDecimal getSpecialDamage(Life attacker, boolean isBoss) {
        return BigDecimal.valueOf(isBoss ? attacker.getBossDamage() : attacker.getNormalDamage());
    }

    public static BigDecimal getTotalDamage(Life attack) {
        return BigDecimal.valueOf(attack.getDamage());
    }

    public static BigDecimal getShowDamage(Life attacker) {
        return BigDecimal.valueOf(attacker.getShowDamage());
    }
}
