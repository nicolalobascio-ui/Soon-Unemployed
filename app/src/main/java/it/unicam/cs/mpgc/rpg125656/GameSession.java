package it.unicam.cs.mpgc.rpg125656;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;
import it.unicam.cs.mpgc.rpg125656.service.BattleServicePort;
import it.unicam.cs.mpgc.rpg125656.service.GamePersistencePort;
import it.unicam.cs.mpgc.rpg125656.service.GameServicePort;

public class GameSession {

    private final GameServicePort gameService;
    private final BattleServicePort battleService;
    private final GamePersistencePort persistenceService;

    private GameState state;

    public GameSession(
            GameServicePort gameService,
            BattleServicePort battleService,
            GamePersistencePort persistenceService
    ) {
        this.gameService = gameService;
        this.battleService = battleService;
        this.persistenceService = persistenceService;
    }

    public void startNewGame(String playerName) {
        state = gameService.startNewGame(playerName);
        persistenceService.save(state);
    }

    public void loadOrStartNew(String playerName) {
        if (!loadGame()) {
            startNewGame(playerName);
        }
    }

    public boolean loadGame() {
        GameState loaded = persistenceService.load();
        if (loaded == null) {
            return false;
        }

        state = loaded;
        return true;
    }

    public boolean saveGame() {
        if (state == null) {
            return false;
        }

        persistenceService.save(state);
        return true;
    }

    public boolean hasSave() {
        return persistenceService.hasSave();
    }

    public void restartGame(String playerName) {
        persistenceService.deleteSave();
        startNewGame(playerName);
    }

    /**
     * Applies a battle action, resolves the turn, and auto-saves.
     * Does nothing if there is no active game or the match is already over.
     */
    public void applyAction(BattleAction action) {
        if (state == null || gameService.isGameOver(state)) {
            return;
        }

        battleService.applyPlayerAction(state, action);
        gameService.resolveCurrentBattle(state);
        persistenceService.save(state);
    }

    public GameState getState() {
        return state;
    }

    public boolean isGameOver() {
        return state != null && gameService.isGameOver(state);
    }

    public void deleteSave() {
        persistenceService.deleteSave();
    }

    public void clearSession() {
        state = null;
    }
}