package com.roger.mmst.constants.converter;

import cn.hutool.core.util.EnumUtil;
import com.roger.mmst.constants.CodeEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

/**
 * @author Roger Liu
 * @date 2024/03/08
 */

@Converter
public abstract class CodeEnumConverter<E extends Enum<E> & CodeEnum<C>, C> implements AttributeConverter<E, C> {

    abstract Class<E> getEnumClass();

    public static <T extends Enum<T> & CodeEnum<O>, O> T of(Class<T> type, O o) {
        return EnumUtil.getEnumMap(type).values().stream().filter(e -> Objects.equals(e.getCode(), o))
                .findFirst().orElse(null);
    }

    @Override
    public C convertToDatabaseColumn(E attribute) {
        return attribute.getCode();
    }

    @Override
    public E convertToEntityAttribute(C dbData) {
        return CodeEnumConverter.of(getEnumClass(), dbData);
    }
}
