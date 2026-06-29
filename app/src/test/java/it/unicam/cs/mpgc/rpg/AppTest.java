/*
 * Test delle entity principali
 */
package it.unicam.cs.mpgc.rpg;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import it.unicam.cs.mpgc.rpg.entity.*;

class AppTest {
    @Test
    void playerInitializesWithCorrectStats() {
        Player player = new Player("John");
        assertEquals(100, player.getStats().getMentalHealth(), "Initial mental health should be 100");
        assertEquals(50, player.getStats().getPatience(), "Initial patience should be 50");
    }

    @Test
    void playerTakesStress() {
        Player player = new Player("John");
        player.applyAction(BattleAction.BE_KIND);
        assertEquals(80, player.getStats().getMentalHealth(), "Mental health should be reduced by 20");
    }

    @Test
    void enemyInitializesWithCorrectAuthority() {
        Enemy enemy = new Enemy("Colleague", 50, false);
        assertEquals(50, enemy.getAuthority(), "Initial authority should be 50");
        assertFalse(enemy.canFirePlayer(), "Irritation should not fire player for regular enemy");
    }

    @Test
    void bossCanFirePlayer() {
        Boss boss = new Boss("Big Boss", 100);
        assertTrue(boss.canFirePlayer(), "Boss should be able to fire player");
    }
}