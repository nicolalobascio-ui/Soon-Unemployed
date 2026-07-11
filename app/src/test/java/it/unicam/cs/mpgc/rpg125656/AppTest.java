/*
 * Test delle entity principali
 */
package it.unicam.cs.mpgc.rpg125656;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void getGreetingReturnsHelloWorld() {
        App app = new App();
        assertEquals("Hello World!", app.getGreeting());
    }

}