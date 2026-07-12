package it.unicam.cs.mpgc.rpg125656;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;
import it.unicam.cs.mpgc.rpg125656.service.GamePersistenceService;
import it.unicam.cs.mpgc.rpg125656.service.BattleService;
import it.unicam.cs.mpgc.rpg125656.service.GameService;

public class GameSession {

    private final GameService gameService;
    private final BattleService battleService;
    private final GamePersistenceService persistenceService;

    private GameState state;

    public GameSession(GameService gameService, BattleService battleService, GamePersistenceService persistenceService) {
        this.gameService = gameService;
        this.battleService = battleService;
        this.persistenceService = persistenceService;
    }

    public void startNewGame(String playerName) {
        state = gameService.startNewGame(playerName);
        persistenceService.save(state);
    }

    public void loadOrStartNew(String playerName) {
        GameState loaded = persistenceService.load();
        if (loaded != null) {
            state = loaded;
            return;
        }

        startNewGame(playerName);
    }

    public void restartGame(String playerName) {
        persistenceService.deleteSave();
        startNewGame(playerName);
    }

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