package it.unicam.cs.mpgc.rpg125656.entity;

public class GameState {
    public enum Outcome {
        IN_PROGRESS,
        VICTORY,
        BURNOUT,
        FIRED
    }

    private final Player player;
    private Enemy currentEnemy;
    private int level;
    private String timeLabel;
    private Outcome outcome;

    public GameState(Player player) {
        this.player = player;
        this.level = 1;
        this.timeLabel = "10:00";
        this.outcome = Outcome.IN_PROGRESS;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getCurrentEnemy() {
        return currentEnemy;
    }

    public void setCurrentEnemy(Enemy currentEnemy) {
        this.currentEnemy = currentEnemy;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(1, level);
    }

    public String getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(String timeLabel) {
        this.timeLabel = timeLabel;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public boolean isFinished() {
        return outcome != Outcome.IN_PROGRESS;
    }
}