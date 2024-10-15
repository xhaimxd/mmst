package com.roger.mmst.entity.item;

import com.roger.mmst.constants.item.EquipType;
import com.roger.mmst.constants.job.JobType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * @author Roger Liu
 * @date 2024/03/08
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@DiscriminatorValue("0")
@Inheritance(strategy = InheritanceType.JOINED)
public class ItemEquip extends Item {

    private EquipType equipType;
    private Integer level;
    private JobType job;
    private Integer maxHp;
    private Integer maxMp;
    private Integer strReq;
    private Integer intReq;
    private Integer dexReq;
    private Integer lukReq;
    private Integer strVal;
    private Integer intVal;
    private Integer dexVal;
    private Integer lukVal;
    private Integer att;
    private Integer matt;
    private Integer def;
    private Integer avoid;
    private Integer speed;
    private Integer bd;
    private Integer ide;
    private Integer damage;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ItemEquip itemEquip = (ItemEquip) o;
        return getId() != null && Objects.equals(getId(), itemEquip.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
