package it.unicam.cs.mpgc.rpg125656;

import org.junit.jupiter.api.Test;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.Player;
import it.unicam.cs.mpgc.rpg125656.entity.Stats;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void playerStartsWithDefaultStats() {
        Player player = new Player("John");

        assertEquals("John", player.getName());
        assertEquals(100, player.getStats().getMentalHealth());
        assertEquals(50, player.getStats().getPatience());
    }

    @Test
    void playerAppliesActionToStats() {
        Player player = new Player("John");

        player.applyAction(BattleAction.PASSIVE_AGGRESSIVE);

        assertEquals(100, player.getStats().getMentalHealth());
        assertEquals(30, player.getStats().getPatience());
    }

    @Test
    void playerCanBecomeExhausted() {
        Player player = new Player("John", new Stats(1, 50));

        assertEquals(false, player.isExhausted());

        player.applyAction(BattleAction.BE_KIND);

        assertEquals(true, player.isExhausted());
    }
}