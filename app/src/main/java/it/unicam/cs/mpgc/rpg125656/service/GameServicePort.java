package it.unicam.cs.mpgc.rpg125656.service;

import it.unicam.cs.mpgc.rpg125656.entity.GameState;

public interface GameServicePort {
    GameState startNewGame(String playerName);

    void resolveCurrentBattle(GameState state);

    void advanceLevel(GameState state);

    boolean isGameOver(GameState state);
}