package it.unicam.cs.mpgc.rpg125656.service;

import it.unicam.cs.mpgc.rpg125656.entity.Boss;
import it.unicam.cs.mpgc.rpg125656.entity.Enemy;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;
import it.unicam.cs.mpgc.rpg125656.entity.Player;

public class GameService implements GameServicePort {

    private final BattleServicePort battleService;

    public GameService() {
        this(new BattleService());
    }

    public GameService(BattleServicePort battleService) {
        this.battleService = battleService;
    }

    @Override
    public GameState startNewGame(String playerName) {
        Player player = new Player(playerName);
        GameState state = new GameState(player);
        state.setLevel(1);
        state.setTimeLabel("10:00");
        state.setCurrentEnemy(createEnemyForLevel(1));
        state.setOutcome(GameState.Outcome.IN_PROGRESS);
        return state;
    }

    public Enemy createEnemyForLevel(int level) {
        return switch (level) {
            case 1 -> new Enemy("Colleague fastidioso", 50, false);
            case 2 -> new Enemy("Project manager", 80, false);
            case 3 -> new Boss("Grande capo", 100);
            default -> new Boss("Grande capo", 100);
        };
    }

    /**
     * Called after each player action. First checks if the game already ended,
     * then advances to the next level if the current enemy was defeated.
     */
    @Override
    public void resolveCurrentBattle(GameState state) {
        if (state == null || state.isFinished()) {
            return;
        }

        battleService.updateOutcome(state);

        if (state.getOutcome() != GameState.Outcome.IN_PROGRESS) {
            return;
        }

        if (state.getCurrentEnemy() != null && state.getCurrentEnemy().isDefeated()) {
            advanceLevel(state);
        }
    }

    /**
     * Moves the game to the next level when the enemy authority reaches zero.
     * If level 3 is already beaten, the outcome is set to VICTORY.
     */
    @Override
    public void advanceLevel(GameState state) {
        if (state == null || state.isFinished()) {
            return;
        }

        if (state.getCurrentEnemy() == null || !state.getCurrentEnemy().isDefeated()) {
            return;
        }

        if (state.getLevel() >= 3) {
            state.setOutcome(GameState.Outcome.VICTORY);
            return;
        }

        int nextLevel = state.getLevel() + 1;
        state.setLevel(nextLevel);
        state.setCurrentEnemy(createEnemyForLevel(nextLevel));
        state.setTimeLabel(getTimeLabelForLevel(nextLevel));
    }

    @Override
    public boolean isGameOver(GameState state) {
        return state != null && state.isFinished();
    }

    private String getTimeLabelForLevel(int level) {
        return switch (level) {
            case 1 -> "10:00";
            case 2 -> "14:00";
            case 3 -> "17:30";
            default -> "18:00";
        };
    }
}