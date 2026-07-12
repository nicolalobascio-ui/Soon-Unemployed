package it.unicam.cs.mpgc.rpg125656;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainWindowController {

    private final GameSession session;

    private final BorderPane root = new BorderPane();
    private final Label titleLabel = new Label("Soon Unemployed");
    private final Label playerLabel = new Label();
    private final Label levelLabel = new Label();
    private final Label timeLabel = new Label();
    private final Label hpLabel = new Label();
    private final Label patienceLabel = new Label();
    private final Label enemyLabel = new Label();
    private final Label authorityLabel = new Label();
    private final Label irritationLabel = new Label();
    private final TextArea dialogueArea = new TextArea();
    private final TextArea logArea = new TextArea();

    private final Button kindButton = new Button("Gentile");
    private final Button passiveButton = new Button("Passivo-aggressiva");
    private final Button rudeButton = new Button("Maleducata");
    private final Button menuButton = new Button("Torna al menù");
    private final Button restartButton = new Button("Riavvia partita");

    private final VBox gamePane = new VBox(12);
    private final VBox menuPane = new VBox(16);

    public MainWindowController(GameSession session) {
        this.session = session;
        buildUi();
        bindActions();
    }

    public Parent getRoot() {
        return root;
    }

    public void initialize() {
        showMenu();
    }

    private void buildUi() {
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        dialogueArea.setEditable(false);
        dialogueArea.setWrapText(true);

        logArea.setEditable(false);
        logArea.setWrapText(true);

        VBox infoBox = new VBox(8,
                titleLabel,
                playerLabel,
                levelLabel,
                timeLabel,
                hpLabel,
                patienceLabel,
                enemyLabel,
                authorityLabel,
                irritationLabel
        );
        infoBox.setPadding(new Insets(16));

        VBox centerBox = new VBox(12,
                new Label("Dialogo"),
                dialogueArea,
                new Label("Log"),
                logArea
        );
        centerBox.setPadding(new Insets(16));

        HBox actionButtons = new HBox(10, kindButton, passiveButton, rudeButton);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setPadding(new Insets(16));

        HBox topButtons = new HBox(10, menuButton, restartButton);
        topButtons.setAlignment(Pos.CENTER_LEFT);
        topButtons.setPadding(new Insets(12, 16, 0, 16));

        gamePane.getChildren().addAll(topButtons, infoBox, centerBox, actionButtons);

        menuPane.setAlignment(Pos.CENTER);
        menuPane.setPadding(new Insets(24));

        Label menuTitle = new Label("Soon Unemployed");
        menuTitle.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        Label menuText = new Label("Premi 'Nuova partita' per iniziare.");
        Button newGameButton = new Button("Nuova partita");

        newGameButton.setOnAction(event -> {
            String playerName = "John";
            session.restartGame(playerName);
            showGame();
            refresh();
            appendLog("Nuova partita avviata.");
        });

        menuPane.getChildren().addAll(menuTitle, menuText, newGameButton);

        root.setCenter(menuPane);
    }

    private void bindActions() {
        kindButton.setOnAction(event -> handleAction(BattleAction.BE_KIND));
        passiveButton.setOnAction(event -> handleAction(BattleAction.PASSIVE_AGGRESSIVE));
        rudeButton.setOnAction(event -> handleAction(BattleAction.RUDE));

        menuButton.setOnAction(event -> {
            session.deleteSave();
            session.clearSession();
            showMenu();
        });

        restartButton.setOnAction(event -> {
            session.restartGame("John");
            showGame();
            refresh();
            appendLog("Partita riavviata.");
        });
    }

    private void handleAction(BattleAction action) {
        GameState state = session.getState();
        if (state == null || session.isGameOver()) {
            return;
        }

        dialogueArea.clear();
        appendDialogue(action);

        int previousLevel = state.getLevel();
        String previousEnemyName = state.getCurrentEnemy().getName();

        session.applyAction(action);
        refresh();
        appendTurnResult(state, previousLevel, previousEnemyName);

        if (session.isGameOver()) {
            appendLog("Partita terminata.");
            disableButtons();
            session.deleteSave();
        }
    }

    private void refresh() {
        GameState state = session.getState();
        if (state == null) {
            return;
        }

        playerLabel.setText("Giocatore: " + state.getPlayer().getName());
        levelLabel.setText("Livello: " + state.getLevel());
        timeLabel.setText("Orario: " + state.getTimeLabel());
        hpLabel.setText("HP mentale: " + state.getPlayer().getStats().getMentalHealth());
        patienceLabel.setText("Pazienza: " + state.getPlayer().getStats().getPatience());
        enemyLabel.setText("Nemico: " + state.getCurrentEnemy().getName());
        authorityLabel.setText("Autorita nemico: " + state.getCurrentEnemy().getAuthority());
        irritationLabel.setText("Irritazione: " + state.getCurrentEnemy().getIrritation());
    }

    private void showMenu() {
        root.setCenter(menuPane);
        clearGameFields();
        kindButton.setDisable(true);
        passiveButton.setDisable(true);
        rudeButton.setDisable(true);
        menuButton.setDisable(true);
        restartButton.setDisable(true);
    }

    private void showGame() {
        root.setCenter(gamePane);
        kindButton.setDisable(false);
        passiveButton.setDisable(false);
        rudeButton.setDisable(false);
        menuButton.setDisable(false);
        restartButton.setDisable(false);
    }

    private void clearGameFields() {
        playerLabel.setText("");
        levelLabel.setText("");
        timeLabel.setText("");
        hpLabel.setText("");
        patienceLabel.setText("");
        enemyLabel.setText("");
        authorityLabel.setText("");
        irritationLabel.setText("");
        dialogueArea.clear();
        logArea.clear();
    }

    private void appendDialogue(BattleAction action) {
        switch (action) {
            case BE_KIND:
                appendDialogueLine("Tu: Certamente, me ne occupo subito.");
                appendDialogueLine("Collega: Perfetto, grazie.");
                appendDialogueLine("Tu: Nessun problema.");
                break;
            case PASSIVE_AGGRESSIVE:
                appendDialogueLine("Tu: Come da mia precedente mail...");
                appendDialogueLine("Collega: ...");
                appendDialogueLine("Tu: Esatto.");
                break;
            case RUDE:
                appendDialogueLine("Tu: No, non lo faccio. Arrangiati.");
                appendDialogueLine("Collega: Come ti permetti?");
                appendDialogueLine("Tu: Ho altro da fare.");
                break;
        }
    }

    private void appendDialogueLine(String text) {
        dialogueArea.appendText(text + "\n");
    }

    private void appendTurnResult(GameState state, int previousLevel, String previousEnemyName) {
        if (state.getOutcome() == GameState.Outcome.BURNOUT) {
            appendLog("Hai esaurito le energie.");
        } else if (state.getOutcome() == GameState.Outcome.FIRED) {
            appendLog("Il boss ti ha licenziato.");
        } else if (state.getOutcome() == GameState.Outcome.VICTORY) {
            appendLog("Hai superato l'ultimo incontro.");
        } else if (state.getLevel() > previousLevel) {
            appendLog("Hai sconfitto " + previousEnemyName + " e sei passato al livello " + state.getLevel() + ".");
        } else {
            appendLog("Turno completato.");
        }
    }

    private void appendLog(String text) {
        logArea.appendText(text + "\n");
    }

    private void disableButtons() {
        kindButton.setDisable(true);
        passiveButton.setDisable(true);
        rudeButton.setDisable(true);
    }
}