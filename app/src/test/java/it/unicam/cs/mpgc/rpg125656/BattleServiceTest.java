package it.unicam.cs.mpgc.rpg125656;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.Boss;
import it.unicam.cs.mpgc.rpg125656.entity.Enemy;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;
import it.unicam.cs.mpgc.rpg125656.entity.Player;
import it.unicam.cs.mpgc.rpg125656.entity.Stats;
import it.unicam.cs.mpgc.rpg125656.service.BattleService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BattleServiceTest {

    private final BattleService battleService = new BattleService();

    @Test
    void applyPlayerActionUpdatesPlayerAndEnemy() {
        GameState state = new GameState(new Player("John"));
        state.setCurrentEnemy(new Enemy("Colleague", 50, false));

        battleService.applyPlayerAction(state, BattleAction.PASSIVE_AGGRESSIVE);

        assertEquals(100, state.getPlayer().getStats().getMentalHealth());
        assertEquals(30, state.getPlayer().getStats().getPatience());
        assertEquals(25, state.getCurrentEnemy().getAuthority());
        assertEquals(5, state.getCurrentEnemy().getIrritation());
        assertEquals(GameState.Outcome.IN_PROGRESS, state.getOutcome());
    }

    @Test
    void updateOutcomeSetsBurnoutWhenPlayerExhausted() {
        GameState state = new GameState(new Player("John", new Stats(20, 50)));
        state.setCurrentEnemy(new Enemy("Colleague", 50, false));

        battleService.applyPlayerAction(state, BattleAction.BE_KIND);

        assertEquals(GameState.Outcome.BURNOUT, state.getOutcome());
    }

    @Test
    void updateOutcomeSetsFiredWhenBossReachesMaxIrritation() {
        GameState state = new GameState(new Player("John"));
        state.setCurrentEnemy(new Boss("Big Boss", 100));

        battleService.applyPlayerAction(state, BattleAction.RUDE);
        battleService.applyPlayerAction(state, BattleAction.RUDE);
        battleService.applyPlayerAction(state, BattleAction.RUDE);

        assertEquals(GameState.Outcome.FIRED, state.getOutcome());
    }

    @Test
    void isBattleWonReturnsTrueWhenEnemyIsDefeated() {
        GameState state = new GameState(new Player("John"));
        Enemy enemy = new Enemy("Colleague", 1, false);
        enemy.applyPlayerAction(BattleAction.RUDE);
        state.setCurrentEnemy(enemy);

        assertEquals(true, battleService.isBattleWon(state));
    }

    @Test
    void isGameOverReflectsFinishedState() {
        GameState state = new GameState(new Player("John"));
        state.setCurrentEnemy(new Enemy("Colleague", 50, false));

        assertEquals(false, battleService.isGameOver(state));

        state.setOutcome(GameState.Outcome.VICTORY);

        assertEquals(true, battleService.isGameOver(state));
    }
}