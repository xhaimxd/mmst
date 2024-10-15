package com.roger.mmst.entity.life;

import com.roger.mmst.entity.BaseEntity;
import com.roger.mmst.entity.item.Item;
import com.roger.mmst.world.Life;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

/**
 * @author Roger Liu
 * @date 2024/03/07
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Monster extends BaseEntity {

    private String name;
    private Integer level;
    private Long hp;
    private Long mp;
    private Long exp;
    private Long att;
    private Integer def;

    @ManyToMany
    @JoinTable(name = "monsterDrop", joinColumns = @JoinColumn(name = "monster_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> drops;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Monster monster = (Monster) o;
        return getId() != null && Objects.equals(getId(), monster.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
