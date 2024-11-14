package com.roger.mmst.domain.entity.item;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ItemEquipStats {
    private Integer strVal = 0;
    private Integer intVal = 0;
    private Integer dexVal = 0;
    private Integer lukVal = 0;
    private Integer att = 0;
    private Integer matt = 0;
    private Integer def = 0;
    private Integer avoid = 0;
    private Integer speed = 0;
    private Integer bd = 0;
    private Integer ide = 0;
    private Integer damage = 0;
}
