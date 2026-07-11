package it.unicam.cs.mpgc.rpg125656;

import org.junit.jupiter.api.Test;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.Boss;
import it.unicam.cs.mpgc.rpg125656.entity.Enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnemyTest {

    @Test
    void enemyStartsWithCorrectValues() {
        Enemy enemy = new Enemy("Colleague", 50, false);

        assertEquals("Colleague", enemy.getName());
        assertEquals(50, enemy.getAuthority());
        assertEquals(0, enemy.getIrritation());
        assertEquals(false, enemy.canFirePlayer());
    }

    @Test
    void enemyAppliesPlayerAction() {
        Enemy enemy = new Enemy("Colleague", 50, false);

        enemy.applyPlayerAction(BattleAction.RUDE);

        assertEquals(10, enemy.getAuthority());
        assertEquals(35, enemy.getIrritation());
    }

    @Test
    void enemyAuthorityAndIrritationAreClamped() {
        Enemy enemy = new Enemy("Colleague", 1, false);

        enemy.applyPlayerAction(BattleAction.RUDE);

        assertEquals(0, enemy.getAuthority());
        assertEquals(35, enemy.getIrritation());
    }

    @Test
    void bossCanFirePlayerAtMaxIrritation() {
        Enemy boss = new Boss("Big Boss", 100);

        boss.applyPlayerAction(BattleAction.RUDE);
        boss.applyPlayerAction(BattleAction.RUDE);
        boss.applyPlayerAction(BattleAction.RUDE);

        assertEquals(true, boss.hasFiredPlayer());
    }

    @Test
    void regularEnemyCannotFirePlayer() {
        Enemy enemy = new Enemy("Colleague", 50, false);

        enemy.applyPlayerAction(BattleAction.RUDE);
        enemy.applyPlayerAction(BattleAction.RUDE);
        enemy.applyPlayerAction(BattleAction.RUDE);
        enemy.applyPlayerAction(BattleAction.RUDE);

        assertEquals(false, enemy.hasFiredPlayer());
    }
}