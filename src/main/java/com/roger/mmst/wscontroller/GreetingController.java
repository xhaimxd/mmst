package com.roger.mmst.wscontroller;

import com.roger.mmst.dto.test.HelloMessage;
import com.roger.mmst.entity.test.Greeting;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendToUser(value = "/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}
