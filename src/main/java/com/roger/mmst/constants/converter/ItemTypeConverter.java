package com.roger.mmst.constants.converter;

import com.roger.mmst.constants.item.ItemType;
import jakarta.persistence.Converter;

/**
 * @author Roger Liu
 * @date 2024/03/08
 */
@Converter(autoApply = true)
public class ItemTypeConverter extends CodeEnumConverter<ItemType, Integer> {
    @Override
    Class<ItemType> getEnumClass() {
        return ItemType.class;
    }
}
