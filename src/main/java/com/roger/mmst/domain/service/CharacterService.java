package com.roger.mmst.domain.service;

import com.roger.mmst.component.factory.CharacterFactory;
import com.roger.mmst.constants.job.Job;
import com.roger.mmst.domain.entity.character.Character;
import com.roger.mmst.domain.entity.character.QCharacter;
import com.roger.mmst.domain.repository.character.CharacterRepo;
import com.roger.mmst.obj.dto.character.CreateCharacterDTO;
import com.roger.mmst.constants.exception.VerifyException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class CharacterService {

    @Resource
    private CharacterRepo characterRepo;
    @Resource
    private CharacterFactory characterFactory;

    public void createCharacter(CreateCharacterDTO dto) {
        if (dto.getJob() != Job.BEGINNER) {
            throw new VerifyException("非法职业");
        }
        QCharacter q = QCharacter.character;
        if (characterRepo.count(q.name.eq(dto.getName())) > 0) {
            throw new VerifyException("角色名重复");
        }
        Character character = characterFactory.create(dto.getName(), dto.getJob());
        characterRepo.save(character);
    }
}
