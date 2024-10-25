package com.roger.mmst.obj.valueobject;

import cn.hutool.core.bean.BeanUtil;
import com.roger.mmst.constants.item.WeaponType;
import com.roger.mmst.domain.entity.character.Character;
import com.roger.mmst.domain.entity.item.ItemEquipWeapon;
import com.roger.mmst.obj.dto.battle.CharacterInfo;
import com.roger.mmst.obj.valueobject.battle.SkillInfo;

public class EntityTransfer {
    private EntityTransfer() {
    }

    public static CharacterInfo toCharacterInfo(Character character) {
        CharacterInfo res = new CharacterInfo();
        BeanUtil.copyProperties(character, res);
        res.setStrVal(character.getBaseStr());
        res.setIntVal(character.getBaseInt());
        res.setDexVal(character.getBaseDex());
        res.setLukVal(character.getBaseLuk());
        res.setWeapon(weaponForTest());
        res.setMainAttackSkill(skillInfoForTest());
        return res;
    }

    private static ItemEquipWeapon weaponForTest() {
        ItemEquipWeapon weapon = new ItemEquipWeapon();
        weapon.setAtt(5);
        weapon.setWeaponType(WeaponType.ONE_HAND_BLUNT);
        return weapon;
    }

    private static SkillInfo skillInfoForTest() {
        SkillInfo skillInfo = new SkillInfo();
        skillInfo.setDamageType(SkillInfo.DamageType.PERCENT);
        skillInfo.setDamage(100d);
        skillInfo.setName("普通攻击");
        skillInfo.setAttackCount(1);
        skillInfo.setAttackNumber(1);
        skillInfo.setAttackType(SkillInfo.AttackType.FRONT);
        return skillInfo;
    }
}
