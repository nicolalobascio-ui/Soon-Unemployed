package it.unicam.cs.mpgc.rpg125656;

import it.unicam.cs.mpgc.rpg125656.service.GamePersistenceService;
import it.unicam.cs.mpgc.rpg125656.service.BattleService;
import it.unicam.cs.mpgc.rpg125656.service.GameService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxApp extends Application {

    @Override
    public void start(Stage stage) {
        GameSession session = new GameSession(
                new GameService(),
                new BattleService(),
                new GamePersistenceService()
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