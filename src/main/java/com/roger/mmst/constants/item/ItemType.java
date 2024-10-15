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
public enum ItemType implements CodeEnum<Integer> {
    EQUIP(0),
    CONSUME(1),
    ETC(2),
    SETUP(3),
    ;
    private final Integer code;
}
