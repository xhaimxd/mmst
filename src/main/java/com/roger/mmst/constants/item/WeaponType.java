package com.roger.mmst.constants.item;

import com.roger.mmst.constants.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeaponType implements CodeEnum<Integer> {
    ONE_HAND_SWORD(100, 1.2),
    ONE_HAND_AXE(101, 1.2),
    ONE_HAND_BLUNT(102, 1.2),
    DAGGER(103, 1.3),
    CLAW(104, 1.75),
    WAND(105, 1.2),
    STAFF(106, 1.2),
    TWO_HAND_SWORD(107, 1.34),
    TWO_HAND_AXE(108, 1.34),
    TWO_HAND_BLUNT(109, 1.34),
    SPEAR(110, 1.49),
    POLEARM(111, 1.49),
    BOW(112, 1.3),
    CROSSBOW(113, 1.35),
    GUN(114, 1.5),
    KNUCKLE(115, 1.7);
    private final Integer code;
    private final Double multiplier;
}
