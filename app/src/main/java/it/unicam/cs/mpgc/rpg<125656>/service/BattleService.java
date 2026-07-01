package it.unicam.cs.mpgc.rpg.service;

import it.unicam.cs.mpgc.rpg.entity.BattleAction;
import it.unicam.cs.mpgc.rpg.entity.Enemy;
import it.unicam.cs.mpgc.rpg.entity.GameState;
import it.unicam.cs.mpgc.rpg.entity.Player;

public class BattleService {

    public void applyPlayerAction(GameState state, BattleAction action) {
        Player player = state.getPlayer();
        Enemy enemy = state.getCurrentEnemy();

        player.applyAction(action);
        enemy.applyPlayerAction(action);

        updateOutcome(state);
    }

    public void updateOutcome(GameState state) {
        Player player = state.getPlayer();
        Enemy enemy = state.getCurrentEnemy();

        if (player.isExhausted()) {
            state.setOutcome(GameState.Outcome.BURNOUT);
            return;
        }

        if (enemy.hasFiredPlayer()) {
            state.setOutcome(GameState.Outcome.FIRED);
            return;
        }

        if (enemy.isDefeated()) {
            if (state.getLevel() >= 3) {
                state.setOutcome(GameState.Outcome.VICTORY);
            }
        }
    }

    public boolean isBattleWon(GameState state) {
        return state.getCurrentEnemy() != null && state.getCurrentEnemy().isDefeated();
    }

    public boolean isGameOver(GameState state) {
        return state.isFinished();
    }
}