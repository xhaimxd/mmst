package com.roger.mmst.component.battle;

import cn.hutool.core.util.RandomUtil;
import com.roger.mmst.obj.dto.battle.CharacterInfo;
import com.roger.mmst.obj.valueobject.battle.MonsterInfo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class BattleRewardCalculator {

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private static final double[] MIN_MESO_COEFFICIENTS = {
            1.0, 1.6, 2.0, 2.4, 2.8, 4.0, 4.8, 5.2, 5.6, 6.0
    };
    private static final double[] MAX_MESO_COEFFICIENTS = {
            1.0, 2.4, 3.0, 3.6, 4.2, 6.0, 7.2, 7.8, 8.4, 9.0
    };

    private static final double[] POSITIVE_LEVEL_DIFF_MODIFIERS = {
            2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0, 20.0,
            25.0, 31.0, 38.0, 46.0, 55.0, 65.0, 76.0, 83.0, 97.0, 100.0
    };

    private static final double[] NEGATIVE_LEVEL_DIFF_MODIFIERS = {
            3.0, 6.0, 9.0, 12.0, 15.0, 18.0, 21.0, 24.0, 27.0, 30.0,
            35.0, 40.0, 45.0, 50.0, 55.0, 60.0, 65.0, 70.0, 75.0, 80.0, 85.0, 90.0, 95.0, 100.0
    };

    public Long getMeso(CharacterInfo characterInfo, MonsterInfo monsterInfo) {
        return BigDecimal.valueOf(characterInfo.getMesoRate())
                .multiply(getBaseMeso(monsterInfo.getLevel()))
                .multiply(getMesoLevelMultiplier(characterInfo.getLevel(), monsterInfo.getLevel()))
                .divide(HUNDRED, 0, RoundingMode.HALF_EVEN)
                .longValue();
    }

    public BigDecimal getBaseMeso(Integer monsterLevel) {
        int index = Math.clamp((monsterLevel - 1) / 10, 0, 9);
        double multiplier = RandomUtil.randomDouble(MIN_MESO_COEFFICIENTS[index], MAX_MESO_COEFFICIENTS[index]);
        return BigDecimal.valueOf(multiplier).multiply(BigDecimal.valueOf(monsterLevel));
    }

    public BigDecimal getMesoLevelMultiplier(Integer charLevel, Integer monsterLevel) {
        int baseDiff = charLevel - monsterLevel;
        int absDiff = Math.abs(baseDiff);
        if (absDiff <= 10) return BigDecimal.ONE;
        double v = baseDiff > 0 ? POSITIVE_LEVEL_DIFF_MODIFIERS[Math.min(baseDiff - 11, 19)] : NEGATIVE_LEVEL_DIFF_MODIFIERS[Math.min(-baseDiff - 11, 23)];
        return BigDecimal.valueOf((100 - v) / 100d);
    }

    public static void main(String[] args) {
        BattleRewardCalculator calculator = new BattleRewardCalculator();
        CharacterInfo c = new CharacterInfo();
        MonsterInfo m = new MonsterInfo();
        for (int i = 0; i < 20; i++) {
            System.out.println(calculator.getMeso(c, m));
        }
    }
}
