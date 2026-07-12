package it.unicam.cs.mpgc.rpg125656;

import it.unicam.cs.mpgc.rpg125656.service.GamePersistenceService;
import it.unicam.cs.mpgc.rpg125656.service.BattleService;
import it.unicam.cs.mpgc.rpg125656.service.GameService;

public class App {

    public static void main(String[] args) {
        GameController controller = new GameController(
                new GameService(),
                new BattleService(),
                new GamePersistenceService(),
                new ConsoleGameView()
        );

        controller.start();
    }
}