package com.roger.mmst.entity.character;

import com.roger.mmst.entity.BaseEntity;
import com.roger.mmst.entity.item.ItemEquip;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * @author Roger Liu
 * @date 2024/03/12
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CharacterEquip extends BaseEntity {

    @OneToOne
    private ItemEquip item;
    @ManyToOne
    private Character character;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CharacterEquip that = (CharacterEquip) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
