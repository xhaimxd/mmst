package com.roger.mmst.domain.entity.item;

import com.roger.mmst.constants.item.EquipType;
import com.roger.mmst.constants.job.JobType;
import jakarta.persistence.*;
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
    private Boolean equipped;
    private Integer level;
    private JobType job;
    private Integer maxHp;
    private Integer maxMp;
    private Integer strReq;
    private Integer intReq;
    private Integer dexReq;
    private Integer lukReq;
    @Embedded
    private ItemEquipStats baseStats = new ItemEquipStats();

    @Embedded
    @AttributeOverride(name = "strVal", column = @Column(name = "flameStrVal"))
    @AttributeOverride(name = "intVal", column = @Column(name = "flameIntVal"))
    @AttributeOverride(name = "dexVal", column = @Column(name = "flameDexVal"))
    @AttributeOverride(name = "lukVal", column = @Column(name = "flameLukVal"))
    @AttributeOverride(name = "att", column = @Column(name = "flameAttVal"))
    @AttributeOverride(name = "matt", column = @Column(name = "flameMattVal"))
    @AttributeOverride(name = "def", column = @Column(name = "flameDefVal"))
    @AttributeOverride(name = "avoid", column = @Column(name = "flameAvoidVal"))
    @AttributeOverride(name = "speed", column = @Column(name = "flameSpeedVal"))
    @AttributeOverride(name = "bd", column = @Column(name = "flameBdVal"))
    @AttributeOverride(name = "ide", column = @Column(name = "flameIdeVal"))
    @AttributeOverride(name = "damage", column = @Column(name = "flameDamageVal"))
    private ItemEquipStats flameStats = new ItemEquipStats();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ItemEquip itemEquip = (ItemEquip) o;
        return getId() != null && Objects.equals(getId(), itemEquip.getId());
    }

    @Override
    public int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public long getAtt() {
        return baseStats.getAtt() + flameStats.getAtt();
    }
}
