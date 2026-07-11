package it.unicam.cs.mpgc.rpg125656.entity;

public class Player {
    private final String name;
    private final Stats stats;

    public Player(String name) {
        this(name, new Stats(100, 50));
    }

    public Player(String name, Stats stats) {
        this.name = name;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public Stats getStats() {
        return stats;
    }

    public void applyAction(BattleAction action) {
        int patienceDelta = action.getPatienceDelta();
        int healthDelta = action.getPlayerMentalHealthDelta();

        if (patienceDelta >= 0) {
            stats.recoverPatience(patienceDelta);
        } else {
            stats.consumePatience(-patienceDelta);
        }

        if (healthDelta >= 0) {
            stats.healMentalHealth(healthDelta);
        } else {
            stats.damageMentalHealth(-healthDelta);
        }
    }

    public boolean isExhausted() {
        return stats.isExhausted();
    }
}