package com.roger.mmst.battle;

import com.roger.mmst.component.battle.DefaultBattleGround;
import com.roger.mmst.dto.battle.CharacterInfo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class BattleTest {

    @Resource
    private DefaultBattleGround defaultBattleGroundObject;

    @Test
    void battle() {
        Thread thread = new Thread(() -> defaultBattleGroundObject.init("test", new CharacterInfo(), 7, 1L));
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {

        }
    }
}
