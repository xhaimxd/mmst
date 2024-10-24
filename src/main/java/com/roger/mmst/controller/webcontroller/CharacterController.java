package com.roger.mmst.controller.webcontroller;

import com.roger.mmst.domain.service.CharacterService;
import com.roger.mmst.obj.dto.R;
import com.roger.mmst.obj.dto.character.CreateCharacterDTO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/character")
@Validated
public class CharacterController {

    @Resource
    private CharacterService characterService;

    @PostMapping("/create")
    public R<Void> create(@RequestBody @Valid CreateCharacterDTO dto) {
        characterService.createCharacter(dto);
        return R.ok();
    }

}
