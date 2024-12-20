package com.roger.mmst.constants.item;

import com.roger.mmst.constants.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Roger Liu
 * @date 2024/03/08
 */
@AllArgsConstructor
@Getter
public enum EquipType implements CodeEnum<Integer> {
    HAT(0),
    TOP(1),
    BOTTOM(2),
    OVERALL(3),
    SHOES(4),
    GLOVES(5),
    SHOULDER(6),
    CAPE(7),
    WEAPON(8),
    SUB_WEAPON(9),
    RING(10),
    BELT(11),
    PENDANT(12),
    EARRING(13),
    EYE(14),
    FACE(15),
    MEDAL(16),
    TOTEM(17),
    EMBLEM(18),
    BADGE(19),
    POCKET(20),
    ;

    private final Integer code;
}
