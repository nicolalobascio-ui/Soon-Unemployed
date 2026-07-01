package it.unicam.cs.mpgc.rpg.entity;

public class Stats {
    private int mentalHealth;
    private int patience;

    public Stats(int mentalHealth, int patience) {
        this.mentalHealth = clamp(mentalHealth, 0, 100);
        this.patience = clamp(patience, 0, 50);
    }

    public int getMentalHealth() {
        return mentalHealth;
    }

    public int getPatience() {
        return patience;
    }

    public void damageMentalHealth(int amount) {
        mentalHealth = clamp(mentalHealth - Math.max(0, amount), 0, 100);
    }

    public void healMentalHealth(int amount) {
        mentalHealth = clamp(mentalHealth + Math.max(0, amount), 0, 100);
    }

    public void consumePatience(int amount) {
        patience = clamp(patience - Math.max(0, amount), 0, 50);
    }

    public void recoverPatience(int amount) {
        patience = clamp(patience + Math.max(0, amount), 0, 50);
    }

    public boolean isExhausted() {
        return mentalHealth <= 0;
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}