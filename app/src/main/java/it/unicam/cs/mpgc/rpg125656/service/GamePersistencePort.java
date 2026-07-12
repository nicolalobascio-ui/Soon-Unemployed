package it.unicam.cs.mpgc.rpg125656.service;

import it.unicam.cs.mpgc.rpg125656.entity.GameState;

public interface GamePersistencePort {
    void save(GameState state);

    GameState load();

    void deleteSave();

    boolean hasSave();
}