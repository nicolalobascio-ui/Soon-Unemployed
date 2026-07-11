package it.unicam.cs.mpgc.rpg125656;

import org.junit.jupiter.api.Test;

import it.unicam.cs.mpgc.rpg125656.entity.Stats;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatsTest {

    @Test
    void constructorClampsValues() {
        Stats stats = new Stats(120, -10);

        assertEquals(100, stats.getMentalHealth());
        assertEquals(0, stats.getPatience());
    }

    @Test
    void damageAndHealRespectBounds() {
        Stats stats = new Stats(50, 25);

        stats.damageMentalHealth(30);
        assertEquals(20, stats.getMentalHealth());

        stats.damageMentalHealth(500);
        assertEquals(0, stats.getMentalHealth());

        stats.healMentalHealth(40);
        assertEquals(40, stats.getMentalHealth());

        stats.healMentalHealth(500);
        assertEquals(100, stats.getMentalHealth());
    }

    @Test
    void consumeAndRecoverPatienceRespectBounds() {
        Stats stats = new Stats(50, 25);

        stats.consumePatience(10);
        assertEquals(15, stats.getPatience());

        stats.consumePatience(500);
        assertEquals(0, stats.getPatience());

        stats.recoverPatience(20);
        assertEquals(20, stats.getPatience());

        stats.recoverPatience(500);
        assertEquals(50, stats.getPatience());
    }

    @Test
    void isExhaustedWhenMentalHealthReachesZero() {
        Stats stats = new Stats(1, 25);

        assertEquals(false, stats.isExhausted());

        stats.damageMentalHealth(1);
        assertEquals(true, stats.isExhausted());
    }
}