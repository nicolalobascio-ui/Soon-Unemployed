package it.unicam.cs.mpgc.rpg125656.service;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;

public interface BattleServicePort {
    void applyPlayerAction(GameState state, BattleAction action);

    void updateOutcome(GameState state);

    boolean isBattleWon(GameState state);

    boolean isGameOver(GameState state);
}