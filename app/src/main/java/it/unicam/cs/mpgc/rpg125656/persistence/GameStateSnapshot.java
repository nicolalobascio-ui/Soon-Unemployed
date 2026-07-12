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

    /**
     * Rebuilds the full GameState from saved values. We create the enemy separately
     * because Boss and Enemy are different classes and that info is not stored directly.
     */
    public GameState toGameState() {
        Player player = new Player(playerName, new Stats(mentalHealth, patience));
        GameState state = new GameState(player);

        state.setLevel(level);
        state.setTimeLabel(timeLabel);
        state.setOutcome(outcome);
        state.setCurrentEnemy(createEnemy());
        return state;
    }

    // Uses the saved flag to decide whether we need a Boss (can fire) or a normal Enemy.
    private Enemy createEnemy() {
        if (enemyCanFire) {
            return new Boss(enemyName, enemyMaxAuthority, enemyAuthority, enemyIrritation);
        }

        return new Enemy(enemyName, enemyMaxAuthority, enemyAuthority, enemyIrritation, false);
    }

    /**
     * Converts the snapshot to a single line for the save file.
     * Text fields are Base64-encoded so a '|' inside a name does not break parsing.
     */
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

    /**
     * Reads one line from the save file and splits it into the 11 expected fields.
     * Throws if the format does not match (wrong number of parts or invalid values).
     */
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