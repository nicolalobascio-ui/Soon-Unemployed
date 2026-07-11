package it.unicam.cs.mpgc.rpg125656;

import org.junit.jupiter.api.Test;

import it.unicam.cs.mpgc.rpg125656.entity.GameState;
import it.unicam.cs.mpgc.rpg125656.entity.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStateTest {

    @Test
    void stateStartsWithDefaultValues() {
        Player player = new Player("John");
        GameState state = new GameState(player);

        assertEquals(player, state.getPlayer());
        assertEquals(1, state.getLevel());
        assertEquals("10:00", state.getTimeLabel());
        assertEquals(GameState.Outcome.IN_PROGRESS, state.getOutcome());
    }

    @Test
    void levelCannotGoBelowOne() {
        GameState state = new GameState(new Player("John"));

        state.setLevel(0);

        assertEquals(1, state.getLevel());
    }

    @Test
    void finishedStateDependsOnOutcome() {
        GameState state = new GameState(new Player("John"));

        assertEquals(false, state.isFinished());

        state.setOutcome(GameState.Outcome.BURNOUT);

        assertEquals(true, state.isFinished());
    }
}