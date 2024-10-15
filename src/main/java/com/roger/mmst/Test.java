package com.roger.mmst;

import com.roger.mmst.component.battle.BattleGround;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Test implements CommandLineRunner {

    @Resource
    private ObjectProvider<BattleGround> battleGroundObjectProvider;

    @Override
    public void run(String... args) throws Exception {
    }
}
