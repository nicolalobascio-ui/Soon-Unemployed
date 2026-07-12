package it.unicam.cs.mpgc.rpg125656.persistence;

import it.unicam.cs.mpgc.rpg125656.entity.Boss;
import it.unicam.cs.mpgc.rpg125656.entity.Enemy;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;
import it.unicam.cs.mpgc.rpg125656.entity.Player;
import it.unicam.cs.mpgc.rpg125656.entity.Stats;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GameStateSnapshot {

    private final String playerName;
    private final int mentalHealth;
    private final int patience;
    private final int level;
    private final String timeLabel;
    private final String enemyName;
    private final int enemyMaxAuthority;
    private final int enemyAuthority;
    private final int enemyIrritation;
    private final boolean enemyCanFire;
    private final GameState.Outcome outcome;

    public GameStateSnapshot(
            String playerName,
            int mentalHealth,
            int patience,
            int level,
            String timeLabel,
            String enemyName,
            int enemyMaxAuthority,
            int enemyAuthority,
            int enemyIrritation,
            boolean enemyCanFire,
            GameState.Outcome outcome
    ) {
        this.playerName = playerName;
        this.mentalHealth = mentalHealth;
        this.patience = patience;
        this.level = level;
        this.timeLabel = timeLabel;
        this.enemyName = enemyName;
        this.enemyMaxAuthority = enemyMaxAuthority;
        this.enemyAuthority = enemyAuthority;
        this.enemyIrritation = enemyIrritation;
        this.enemyCanFire = enemyCanFire;
        this.outcome = outcome;
    }

    public static GameStateSnapshot from(GameState state) {
        Enemy enemy = state.getCurrentEnemy();

        return new GameStateSnapshot(
                state.getPlayer().getName(),
                state.getPlayer().getStats().getMentalHealth(),
                state.getPlayer().getStats().getPatience(),
                state.getLevel(),
                state.getTimeLabel(),
                enemy.getName(),
                enemy.getMaxAuthority(),
                enemy.getAuthority(),
                enemy.getIrritation(),
                enemy.canFirePlayer(),
                state.getOutcome()
        );
    }

    public GameState toGameState() {
        Player player = new Player(playerName, new Stats(mentalHealth, patience));
        GameState state = new GameState(player);

        state.setLevel(level);
        state.setTimeLabel(timeLabel);
        state.setOutcome(outcome);
        state.setCurrentEnemy(createEnemy());
        return state;
    }

    private Enemy createEnemy() {
        if (enemyCanFire) {
            return new Boss(enemyName, enemyMaxAuthority, enemyAuthority, enemyIrritation);
        }

        return new Enemy(enemyName, enemyMaxAuthority, enemyAuthority, enemyIrritation, false);
    }

    public String toFileFormat() {
        return String.join("|",
                encode(playerName),
                String.valueOf(mentalHealth),
                String.valueOf(patience),
                String.valueOf(level),
                encode(timeLabel),
                encode(enemyName),
                String.valueOf(enemyMaxAuthority),
                String.valueOf(enemyAuthority),
                String.valueOf(enemyIrritation),
                String.valueOf(enemyCanFire),
                outcome.name()
        );
    }

    public static GameStateSnapshot fromFileFormat(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 11) {
            throw new IllegalArgumentException("Invalid save format");
        }

        return new GameStateSnapshot(
                decode(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3]),
                decode(parts[4]),
                decode(parts[5]),
                Integer.parseInt(parts[6]),
                Integer.parseInt(parts[7]),
                Integer.parseInt(parts[8]),
                Boolean.parseBoolean(parts[9]),
                GameState.Outcome.valueOf(parts[10])
        );
    }

    private static String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private static String decode(String value) {
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }
}