package it.unicam.cs.mpgc.rpg125656.service;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.Enemy;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;
import it.unicam.cs.mpgc.rpg125656.entity.Player;

public class BattleService implements BattleServicePort {

    @Override
    public void applyPlayerAction(GameState state, BattleAction action) {
        if (state == null || action == null || state.isFinished() || state.getCurrentEnemy() == null) {
            return;
        }

        Player player = state.getPlayer();
        Enemy enemy = state.getCurrentEnemy();

        player.applyAction(action);
        enemy.applyPlayerAction(action);

        updateOutcome(state);
    }

    @Override
    public void updateOutcome(GameState state) {
        if (state == null || state.getCurrentEnemy() == null || state.isFinished()) {
            return;
        }

        Player player = state.getPlayer();
        Enemy enemy = state.getCurrentEnemy();

        if (player.isExhausted()) {
            state.setOutcome(GameState.Outcome.BURNOUT);
            return;
        }

        if (enemy.hasFiredPlayer()) {
            state.setOutcome(GameState.Outcome.FIRED);
        }
    }

    @Override
    public boolean isBattleWon(GameState state) {
        return state != null
                && state.getCurrentEnemy() != null
                && state.getCurrentEnemy().isDefeated();
    }

    @Override
    public boolean isGameOver(GameState state) {
        return state != null && state.isFinished();
    }
}