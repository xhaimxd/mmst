package com.roger.mmst.domain.entity.character;

import com.roger.mmst.constants.job.Job;
import com.roger.mmst.domain.entity.AuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
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
public class Character extends AuditEntity {
    private String name;
    private Job job = Job.BEGINNER;
    private Integer level = 1;
    private Integer baseHp = 50;
    private Integer hp = 50;
    private Integer baseMp = 50;
    private Integer mp = 50;
    private Integer baseStr = 4;
    private Integer baseDex = 4;
    private Integer baseInt = 4;
    private Integer baseLuk = 4;
    private Integer baseSpeed = 100;
    private Integer baseAvoid = 0;
    private Integer statPoint = 0;
    private Integer skillPoint = 0;

    @OneToMany
    @ToString.Exclude
    private List<CharacterEquip> equips;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Character character = (Character) o;
        return getId() != null && Objects.equals(getId(), character.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
