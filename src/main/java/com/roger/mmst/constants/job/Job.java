package com.roger.mmst.constants.job;

import com.roger.mmst.constants.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Roger Liu
 * @date 2024/03/19
 */
@Getter
@AllArgsConstructor
public enum Job implements CodeEnum<Integer> {
    BEGINNER("新手", null, 0),

    WARRIOR("战士", JobType.WARRIOR, 100),

    FIGHTER("剑客", JobType.WARRIOR, 110),
    CRUSADER("勇士", JobType.WARRIOR, 111),
    HERO("英雄", JobType.WARRIOR, 112),

    PAGE("准骑士", JobType.WARRIOR, 120),
    WHITE_KNIGHT("骑士", JobType.WARRIOR, 121),
    PALADIN("圣骑士", JobType.WARRIOR, 122),

    SPEARMAN("枪战士", JobType.WARRIOR, 130),
    BERSERKER("狂战士", JobType.WARRIOR, 131),
    DARK_KNIGHT("黑骑士", JobType.WARRIOR, 132),

    MAGICIAN("魔法师", JobType.MAGICIAN, 200),

    WIZARD_FP("法师（火/毒）", JobType.MAGICIAN, 210),
    MAGE_FP("巫师（火/毒）", JobType.MAGICIAN, 211),
    ARCH_MAGE_FP("魔导师（火/毒）", JobType.MAGICIAN, 212),

    WIZARD_IL("法师（冰/雷）", JobType.MAGICIAN, 220),
    MAGE_IL("巫师（冰/雷）", JobType.MAGICIAN, 221),
    ARCH_MAGE_IL("魔导师（冰/雷）", JobType.MAGICIAN, 222),

    CLERIC("牧师", JobType.MAGICIAN, 230),
    PRIEST("祭司", JobType.MAGICIAN, 231),
    BISHOP("主教", JobType.MAGICIAN, 232),


    ARCHER("弓箭手", JobType.ARCHER, 300),

    HUNTER("猎人", JobType.ARCHER, 310),
    RANGER("射手", JobType.ARCHER, 311),
    BOWMASTER("神射手", JobType.ARCHER, 312),

    CROSSBOWMAN("弩弓手", JobType.ARCHER, 320),
    SNIPER("游侠", JobType.ARCHER, 321),
    MARKSMAN("箭神", JobType.ARCHER, 322),

    ANCIENT_ARCHER("古代射手", JobType.ARCHER, 330),
    SOULCHASER("追逐者", JobType.ARCHER, 331),
    PATHFINDER("古迹猎人", JobType.ARCHER, 332),


    THIEF("飞侠", JobType.THIEF, 400),

    ASSASSIN("刺客", JobType.THIEF, 410),
    HERMIT("无影人", JobType.THIEF, 411),
    DARK_LOAD("隐士", JobType.THIEF, 412),

    BANDIT("侠客", JobType.THIEF, 420),
    CHIEF_BANDIT("独行客", JobType.THIEF, 421),
    SHADOWER("侠盗", JobType.THIEF, 422),

    BLADE_ACOLYTE("双刀客", JobType.THIEF, 430),
    BLADE_LOAD("血刀", JobType.THIEF, 431),
    BLADE_MASTER("暗影双刀", JobType.THIEF, 432),


    PIRATE("海盗", JobType.PIRATE, 500),

    BRAWLER("拳手", JobType.PIRATE, 510),
    MARAUDER("斗士", JobType.PIRATE, 511),
    BUCCANEER("冲锋队长", JobType.PIRATE, 512),

    GUNSLINGER("火枪手", JobType.PIRATE, 520),
    OUTLAW("大副", JobType.PIRATE, 521),
    CORSAIR("船长", JobType.PIRATE, 522),

    CANNPNEER("火炮手", JobType.PIRATE, 530),
    CANNON_TROOPER("毁灭炮手", JobType.PIRATE, 531),
    CANNON_MASTER("神炮王", JobType.PIRATE, 532),


    ;

    private final String name;
    private final JobType type;
    private final Integer code;

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