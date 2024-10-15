package com.roger.mmst.constants.job;

import com.roger.mmst.constants.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Roger Liu
 * @date 2024/03/08
 */
@AllArgsConstructor
@Getter
public enum JobType implements CodeEnum<Integer> {
    WARRIOR(100),
    MAGICIAN(200),
    ARCHER(300),
    THIEF(400),
    PIRATE(500),
    ;

    private final Integer code;
}
