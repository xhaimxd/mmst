package com.roger.mmst.constants;

import com.roger.mmst.obj.valueobject.character.Character;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.ToIntFunction;

@AllArgsConstructor
@Getter
public enum Stat {
    STR("strVal"),
    DEX("dexVal"),
    INT("intVal"),
    LUK("lukVal"),
    HP("maxHp");

    private final String propertyName;

    public static final ToIntFunction<Character> getIntVal = Character::getIntVal;
    public static final ToIntFunction<Character> getDexVal = Character::getDexVal;
    public static final ToIntFunction<Character> getStrVal = Character::getStrVal;
    public static final ToIntFunction<Character> getLukVal = Character::getLukVal;

    public static final ToIntFunction<Character> getStrDexVal = character -> character.getStrVal() + character.getDexVal();
}
