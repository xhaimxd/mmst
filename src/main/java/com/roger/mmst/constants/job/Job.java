package com.roger.mmst.constants.job;

import com.roger.mmst.constants.CodeEnum;
import com.roger.mmst.obj.valueobject.character.Character;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntFunction;

import static com.roger.mmst.constants.Stat.*;

/**
 * @author Roger Liu
 * @date 2024/03/19
 */
@Getter
@AllArgsConstructor
public enum Job implements CodeEnum<Integer> {
    //冒险家
    BEGINNER("新手", null, 0, getStrVal, character -> 0),

    WARRIOR("战士", JobType.WARRIOR, 100, getStrVal, getDexVal),

    FIGHTER("剑客", JobType.WARRIOR, 110, getStrVal, getDexVal),
    CRUSADER("勇士", JobType.WARRIOR, 111, getStrVal, getDexVal),
    HERO("英雄", JobType.WARRIOR, 112, getStrVal, getDexVal),

    PAGE("准骑士", JobType.WARRIOR, 120, getStrVal, getDexVal),
    WHITE_KNIGHT("骑士", JobType.WARRIOR, 121, getStrVal, getDexVal),
    PALADIN("圣骑士", JobType.WARRIOR, 122, getStrVal, getDexVal),

    SPEARMAN("枪战士", JobType.WARRIOR, 130, getStrVal, getDexVal),
    BERSERKER("狂战士", JobType.WARRIOR, 131, getStrVal, getDexVal),
    DARK_KNIGHT("黑骑士", JobType.WARRIOR, 132, getStrVal, getDexVal),

    MAGICIAN("魔法师", JobType.MAGICIAN, 200, getIntVal, getLukVal),

    WIZARD_FP("法师（火/毒）", JobType.MAGICIAN, 210, getIntVal, getLukVal),
    MAGE_FP("巫师（火/毒）", JobType.MAGICIAN, 211, getIntVal, getLukVal),
    ARCH_MAGE_FP("魔导师（火/毒）", JobType.MAGICIAN, 212, getIntVal, getLukVal),

    WIZARD_IL("法师（冰/雷）", JobType.MAGICIAN, 220, getIntVal, getLukVal),
    MAGE_IL("巫师（冰/雷）", JobType.MAGICIAN, 221, getIntVal, getLukVal),
    ARCH_MAGE_IL("魔导师（冰/雷）", JobType.MAGICIAN, 222, getIntVal, getLukVal),

    CLERIC("牧师", JobType.MAGICIAN, 230, getIntVal, getLukVal),
    PRIEST("祭司", JobType.MAGICIAN, 231, getIntVal, getLukVal),
    BISHOP("主教", JobType.MAGICIAN, 232, getIntVal, getLukVal),


    ARCHER("弓箭手", JobType.ARCHER, 300, getDexVal, getStrVal),

    HUNTER("猎人", JobType.ARCHER, 310, getDexVal, getStrVal),
    RANGER("射手", JobType.ARCHER, 311, getDexVal, getStrVal),
    BOWMASTER("神射手", JobType.ARCHER, 312, getDexVal, getStrVal),

    CROSSBOWMAN("弩弓手", JobType.ARCHER, 320, getDexVal, getStrVal),
    SNIPER("游侠", JobType.ARCHER, 321, getDexVal, getStrVal),
    MARKSMAN("箭神", JobType.ARCHER, 322, getDexVal, getStrVal),

    ANCIENT_ARCHER("古代射手", JobType.ARCHER, 330, getDexVal, getStrVal),
    SOULCHASER("追逐者", JobType.ARCHER, 331, getDexVal, getStrVal),
    PATHFINDER("古迹猎人", JobType.ARCHER, 332, getDexVal, getStrVal),


    THIEF("飞侠", JobType.THIEF, 400, getLukVal, getDexVal),

    ASSASSIN("刺客", JobType.THIEF, 410, getLukVal, getDexVal),
    HERMIT("无影人", JobType.THIEF, 411, getLukVal, getDexVal),
    DARK_LOAD("隐士", JobType.THIEF, 412, getLukVal, getDexVal),

    BANDIT("侠客", JobType.THIEF, 420, getLukVal, getStrDexVal),
    CHIEF_BANDIT("独行客", JobType.THIEF, 421, getLukVal, getStrDexVal),
    SHADOWER("侠盗", JobType.THIEF, 422, getLukVal, getStrDexVal),

    BLADE_ACOLYTE("双刀客", JobType.THIEF, 430, getLukVal, getStrDexVal),
    BLADE_LOAD("血刀", JobType.THIEF, 431, getLukVal, getStrDexVal),
    BLADE_MASTER("暗影双刀", JobType.THIEF, 432, getLukVal, getStrDexVal),


    PIRATE("海盗", JobType.PIRATE, 500, getStrVal, getDexVal),

    BRAWLER("拳手", JobType.PIRATE, 510, getStrVal, getDexVal),
    MARAUDER("斗士", JobType.PIRATE, 511, getStrVal, getDexVal),
    BUCCANEER("冲锋队长", JobType.PIRATE, 512, getStrVal, getDexVal),

    GUNSLINGER("火枪手", JobType.PIRATE, 520, getDexVal, getStrVal),
    OUTLAW("大副", JobType.PIRATE, 521, getDexVal, getStrVal),
    CORSAIR("船长", JobType.PIRATE, 522, getDexVal, getStrVal),

    CANNPNEER("火炮手", JobType.PIRATE, 530, getStrVal, getDexVal),
    CANNON_TROOPER("毁灭炮手", JobType.PIRATE, 531, getStrVal, getDexVal),
    CANNON_MASTER("神炮王", JobType.PIRATE, 532, getStrVal, getDexVal),


    ;

    private final String name;
    private final JobType type;
    private final Integer code;
    private final ToIntFunction<Character> primaryStat;
    private final ToIntFunction<Character> secondStat;

    public static List<Job> findExploreSecondJob(JobType type) {
        return Arrays.stream(values()).filter(j -> j.type == type)
                .filter(j -> j.code % 100 != 0)
                .filter(j -> j.code % 10 == 0)
                .filter(j -> {
                    //暂不开放双刀和炮王
                    return j != BLADE_ACOLYTE && j != CANNPNEER;
                }).toList();
    }
}
