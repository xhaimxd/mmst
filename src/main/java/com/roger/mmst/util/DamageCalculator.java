package com.roger.mmst.util;

import cn.hutool.core.util.RandomUtil;
import com.roger.mmst.world.Life;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DamageCalculator {

    public static final BigDecimal ONE = BigDecimal.ONE;
    public static final BigDecimal ZERO = BigDecimal.ZERO;
    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private DamageCalculator() {

    }

    public static Long getDamage(Life attacker, Life defender) {
        BigDecimal hitDamage = getHitDamage(attacker, defender.isBoss());
        if (RandomUtil.randomDouble(0, 1) < attacker.getCriticalRate()) {
            hitDamage = hitDamage.multiply(getCriticalDamageMultiplier(attacker));
        }
        if (defender.isBoss()) {
            hitDamage = hitDamage.multiply(getDefMultiplier(attacker, defender));
        }
        return hitDamage.longValue();
    }

    public static BigDecimal getCriticalDamageMultiplier(Life attacker) {
        if (attacker.getCriticalDamage() == -1) return ZERO;
        return ONE.add(
                BigDecimal.valueOf(RandomUtil.randomInt(20, 51) + attacker.getCriticalDamage())
                        .divide(HUNDRED, RoundingMode.HALF_EVEN)
        );
    }

    public static BigDecimal getDefMultiplier(Life attacker, Life defender) {
        return ONE.subtract(
                BigDecimal.valueOf(defender.getDefense()).divide(HUNDRED, RoundingMode.HALF_EVEN)
                        .multiply(ONE.subtract(
                                BigDecimal.valueOf(attacker.getDefenseIgnore()).divide(HUNDRED, RoundingMode.HALF_EVEN)
                        ))
        );
    }

    public static BigDecimal getHitDamage(Life attacker, boolean isBoss) {
        BigDecimal totalDamage = getTotalDamage(attacker);
        BigDecimal skillDamage = BigDecimal.ZERO;
        BigDecimal specialDamage = getSpecialDamage(attacker, isBoss);
        return getShowDamage(attacker).divide(ONE.add(totalDamage.divide(HUNDRED, RoundingMode.HALF_EVEN)), RoundingMode.HALF_EVEN)
                .multiply(skillDamage.divide(HUNDRED, RoundingMode.HALF_EVEN))
                .multiply(ONE.add(totalDamage.add(specialDamage).divide(HUNDRED, RoundingMode.HALF_EVEN)));
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
