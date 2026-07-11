package it.unicam.cs.mpgc.rpg125656;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.Boss;
import it.unicam.cs.mpgc.rpg125656.entity.Enemy;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;
import it.unicam.cs.mpgc.rpg125656.entity.Player;
import it.unicam.cs.mpgc.rpg125656.service.GameService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameServiceTest {

    private final GameService gameService = new GameService();

    @Test
    void startNewGameInitializesFirstLevel() {
        GameState state = gameService.startNewGame("John");

        assertEquals("John", state.getPlayer().getName());
        assertEquals(1, state.getLevel());
        assertEquals("10:00", state.getTimeLabel());
        assertEquals("Colleague fastidioso", state.getCurrentEnemy().getName());
        assertEquals(50, state.getCurrentEnemy().getAuthority());
        assertEquals(GameState.Outcome.IN_PROGRESS, state.getOutcome());
    }

    @Test
    void createEnemyForLevelReturnsCorrectEnemy() {
        Enemy levelOne = gameService.createEnemyForLevel(1);
        Enemy levelTwo = gameService.createEnemyForLevel(2);
        Enemy levelThree = gameService.createEnemyForLevel(3);

        assertEquals("Colleague fastidioso", levelOne.getName());
        assertEquals("Project manager", levelTwo.getName());
        assertTrue(levelThree instanceof Boss);
    }

    @Test
    void advanceLevelMovesToSecondLevel() {
        GameState state = new GameState(new Player("John"));
        state.setLevel(1);

        Enemy defeatedEnemy = new Enemy("Test", 1, false);
        defeatedEnemy.applyPlayerAction(BattleAction.RUDE);
        state.setCurrentEnemy(defeatedEnemy);

        gameService.advanceLevel(state);

        assertEquals(2, state.getLevel());
        assertEquals("14:00", state.getTimeLabel());
        assertEquals("Project manager", state.getCurrentEnemy().getName());
        assertEquals(GameState.Outcome.IN_PROGRESS, state.getOutcome());
    }

    @Test
    void advanceLevelAtThirdLevelSetsVictory() {
        GameState state = new GameState(new Player("John"));
        state.setLevel(3);

        Enemy defeatedEnemy = new Enemy("Test", 1, false);
        defeatedEnemy.applyPlayerAction(BattleAction.RUDE);
        state.setCurrentEnemy(defeatedEnemy);

        gameService.advanceLevel(state);

        assertEquals(GameState.Outcome.VICTORY, state.getOutcome());
    }

    @Test
    void resolveCurrentBattleAdvancesWhenEnemyIsDefeated() {
        GameState state = new GameState(new Player("John"));
        state.setLevel(1);

        Enemy defeatedEnemy = new Enemy("Test", 1, false);
        defeatedEnemy.applyPlayerAction(BattleAction.RUDE);
        state.setCurrentEnemy(defeatedEnemy);

        gameService.resolveCurrentBattle(state);

        assertEquals(2, state.getLevel());
        assertEquals("14:00", state.getTimeLabel());
        assertEquals("Project manager", state.getCurrentEnemy().getName());
    }
}