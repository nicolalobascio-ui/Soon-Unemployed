package it.unicam.cs.mpgc.rpg125656;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;
import it.unicam.cs.mpgc.rpg125656.service.BattleServicePort;
import it.unicam.cs.mpgc.rpg125656.service.GamePersistencePort;
import it.unicam.cs.mpgc.rpg125656.service.GameServicePort;

public class GameController {

    private final GameServicePort gameService;
    private final BattleServicePort battleService;
    private final GamePersistencePort persistenceService;
    private final GameView gameView;

    public GameController(
            GameServicePort gameService,
            BattleServicePort battleService,
            GamePersistencePort persistenceService,
            GameView gameView
    ) {
        this.gameService = gameService;
        this.battleService = battleService;
        this.persistenceService = persistenceService;
        this.gameView = gameView;
    }

    public void start() {
        gameView.showHeader();

        GameState state = loadOrCreateGame();

        while (!gameService.isGameOver(state)) {
            gameView.showTurnState(state);
            BattleAction action = gameView.askAction();
            gameView.showActionChosen(action);
            gameView.showDialogue(action);

            int previousLevel = state.getLevel();
            String previousEnemyName = state.getCurrentEnemy().getName();

            battleService.applyPlayerAction(state, action);
            gameService.resolveCurrentBattle(state);

            gameView.showTurnResult(state, previousLevel, previousEnemyName);
            persistenceService.save(state);
        }

        gameView.showFinalOutcome(state);
        persistenceService.deleteSave();
    }

    private GameState loadOrCreateGame() {
        GameState loadedGame = persistenceService.load();
        if (loadedGame != null) {
            return loadedGame;
        }

        String playerName = gameView.askPlayerName();
        GameState newGame = gameService.startNewGame(playerName);
        persistenceService.save(newGame);
        return newGame;
    }
}