package it.unicam.cs.mpgc.rpg125656.entity;

public class Enemy {
    private final String name;
    private final int maxAuthority;
    private int authority;
    private int irritation;
    private final boolean irritationCanFire;

    public Enemy(String name, int maxAuthority, boolean irritationCanFire) {
        this.name = name;
        this.maxAuthority = Math.max(1, maxAuthority);
        this.authority = this.maxAuthority;
        this.irritationCanFire = irritationCanFire;
        this.irritation = 0;
    }

    public String getName() {
        return name;
    }

    public int getAuthority() {
        return authority;
    }

    public int getIrritation() {
        return irritation;
    }

    public boolean canFirePlayer() {
        return irritationCanFire;
    }

    public void applyPlayerAction(BattleAction action) {
        authority = clamp(authority + action.getEnemyAuthorityDelta(), 0, maxAuthority);
        irritation = clamp(irritation + action.getEnemyIrritationDelta(), 0, 100);
    }

    public boolean isDefeated() {
        return authority <= 0;
    }

    public boolean hasFiredPlayer() {
        return irritationCanFire && irritation >= 100;
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}