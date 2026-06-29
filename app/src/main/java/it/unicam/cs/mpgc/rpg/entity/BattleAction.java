package it.unicam.cs.mpgc.rpg.entity;

public enum BattleAction {
    BE_KIND("Certamente, me ne occupo subito", 15, -20, -5, -15),
    PASSIVE_AGGRESSIVE("Come da mia precedente mail...", -20, 0, -25, 5),
    RUDE("No, non lo faccio. Arrangiati.", 0, 10, -40, 35);

    private final String label;
    private final int patienceDelta;
    private final int playerMentalHealthDelta;
    private final int enemyAuthorityDelta;
    private final int enemyIrritationDelta;

    BattleAction(String label, int patienceDelta, int playerMentalHealthDelta, int enemyAuthorityDelta, int enemyIrritationDelta) {
        this.label = label;
        this.patienceDelta = patienceDelta;
        this.playerMentalHealthDelta = playerMentalHealthDelta;
        this.enemyAuthorityDelta = enemyAuthorityDelta;
        this.enemyIrritationDelta = enemyIrritationDelta;
    }

    public String getLabel() {
        return label;
    }

    public int getPatienceDelta() {
        return patienceDelta;
    }

    public int getPlayerMentalHealthDelta() {
        return playerMentalHealthDelta;
    }

    public int getEnemyAuthorityDelta() {
        return enemyAuthorityDelta;
    }

    public int getEnemyIrritationDelta() {
        return enemyIrritationDelta;
    }
}