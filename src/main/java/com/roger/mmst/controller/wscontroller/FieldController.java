package com.roger.mmst.controller.wscontroller;

import com.roger.mmst.component.conf.security.JWT;
import com.roger.mmst.constants.exception.WsNotifyException;
import com.roger.mmst.domain.service.FieldService;
import com.roger.mmst.obj.dto.R;
import com.roger.mmst.obj.dto.battle.BattleBeginDTO;
import com.roger.mmst.obj.dto.message.LayerMessage;
import jakarta.annotation.Resource;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class FieldController {

    @Resource
    private FieldService fieldService;
    @Resource
    private JWT jwt;

    @MessageMapping("/field/battle/begin")
    public void battleBegin(@Header(SimpMessageHeaderAccessor.SESSION_ID_HEADER) String sessionId,
                            @Header("Authorization") String token,
                            BattleBeginDTO message) {
        Long userId = jwt.getId(token);
        fieldService.battleBegin(userId, sessionId, message);
    }

    @MessageMapping("/field/battle/stop")
    public void battleStop(@Header("Authorization") String token) {
        Long userId = jwt.getId(token);
        fieldService.battleStop(userId);
    }

    @MessageExceptionHandler(WsNotifyException.class)
    @SendToUser(value = "/field/error", broadcast = false)
    public R<LayerMessage> handleException(Exception message) {
        return R.ok(LayerMessage.of(message.getMessage()));
    }
}
