package it.unicam.cs.mpgc.rpg125656;

import it.unicam.cs.mpgc.rpg125656.service.BattleService;
import it.unicam.cs.mpgc.rpg125656.service.GamePersistenceService;
import it.unicam.cs.mpgc.rpg125656.service.GameService;
import it.unicam.cs.mpgc.rpg125656.service.BattleServicePort;
import it.unicam.cs.mpgc.rpg125656.service.GamePersistencePort;
import it.unicam.cs.mpgc.rpg125656.service.GameServicePort;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxApp extends Application {

    @Override
    public void start(Stage stage) {
        GameServicePort gameService = new GameService();
        BattleServicePort battleService = new BattleService();
        GamePersistencePort persistenceService = new GamePersistenceService();

        GameSession session = new GameSession(
                gameService,
                battleService,
                persistenceService
        );

        MainWindowController controller = new MainWindowController(session);

        Scene scene = new Scene(controller.getRoot(), 900, 650);
        stage.setTitle("Soon Unemployed");
        stage.setScene(scene);
        stage.show();

        controller.initialize();
    }

    public static void main(String[] args) {
        launch(args);
    }
}