package com.roger.mmst.battle;

import com.roger.mmst.component.battle.DefaultBattleGround;
import com.roger.mmst.constants.item.WeaponType;
import com.roger.mmst.obj.dto.battle.CharacterInfo;
import com.roger.mmst.domain.entity.item.ItemEquipWeapon;
import com.roger.mmst.obj.valueobject.battle.SkillInfo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BattleTest {

    @Resource
    private DefaultBattleGround defaultBattleGroundObject;

    @Test
    void battle() {
        CharacterInfo characterInfo = new CharacterInfo();
        characterInfo.setMainAttackSkill(getSkill());
        characterInfo.setWeapon(getWeapon());
        Thread thread = new Thread(() -> defaultBattleGroundObject.init("test", characterInfo, 3, 4, 1L));
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {

        }
    }

    private ItemEquipWeapon getWeapon() {
        ItemEquipWeapon itemEquipWeapon = new ItemEquipWeapon();
        itemEquipWeapon.setAtt(10);
        itemEquipWeapon.setMatt(10);
        itemEquipWeapon.setWeaponType(WeaponType.ONE_HAND_AXE);
        return itemEquipWeapon;
    }

    private SkillInfo getSkill() {
        SkillInfo skillInfo = new SkillInfo();
        skillInfo.setName("战士一转不知道叫什么中文名姑且叫强力攻击吧的技能");
        skillInfo.setAttackCount(2);
        skillInfo.setAttackNumber(4);
        skillInfo.setDamageType(SkillInfo.DamageType.PERCENT);
        skillInfo.setAttackType(SkillInfo.AttackType.FRONT);
        skillInfo.setDamage(13d);
        return skillInfo;
    }
}
