package it.unicam.cs.mpgc.rpg125656;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppTest {

    @Test
    void appCanBeCreatedWithoutErrors() {
        assertDoesNotThrow(App::new);
    }
}