package com.roger.mmst.controller.wscontroller;

import com.roger.mmst.domain.service.FieldService;
import com.roger.mmst.obj.dto.battle.BattleBeginDTO;
import jakarta.annotation.Resource;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class FieldController {

    @Resource
    private FieldService fieldService;

    @MessageMapping("/field/battle/begin")
    public void greeting(@Header(SimpMessageHeaderAccessor.SESSION_ID_HEADER) String sessionId, BattleBeginDTO message) {
        fieldService.battleBegin(sessionId, message);
    }
}
