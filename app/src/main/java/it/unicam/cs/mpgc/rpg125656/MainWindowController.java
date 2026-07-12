package it.unicam.cs.mpgc.rpg125656;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class MainWindowController {

    private static final String ROOT_STYLE = "-fx-background-color: #F4F1EA;";
    private static final String TITLE_STYLE = "-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #2B2B2B;";
    private static final String TEXT_STYLE = "-fx-text-fill: #2B2B2B;";
    private static final String PANEL_STYLE = "-fx-border-color: #D7CEC3; -fx-border-width: 1; -fx-background-color: #FFF9F0; -fx-padding: 12;";
    private static final String MENU_TEXT_STYLE = "-fx-text-fill: #2B2B2B; -fx-font-size: 14px;";
    private static final String TEXT_AREA_STYLE = "-fx-control-inner-background: #FFFDF8; -fx-background-color: #FFFDF8; -fx-text-fill: #2B2B2B;";
    private static final String PRIMARY_BUTTON_STYLE = "-fx-background-color: #B5524B; -fx-text-fill: white;";
    private static final String SECONDARY_BUTTON_STYLE = "-fx-background-color: #5F7A8A; -fx-text-fill: white;";
    private static final String NEUTRAL_BUTTON_STYLE = "-fx-background-color: #D7CEC3; -fx-text-fill: #2B2B2B;";

    private final GameSession session;

    private final BorderPane root = new BorderPane();
    private final Label titleLabel = new Label("Soon Unemployed");
    private final Label levelLabel = new Label();
    private final Label timeLabel = new Label();

    private final Label playerNameLabel = new Label();
    private final Label hpLabel = new Label();
    private final Label patienceLabel = new Label();

    private final Label enemyNameLabel = new Label();
    private final Label authorityLabel = new Label();
    private final Label irritationLabel = new Label();

    private final TextArea dialogueArea = new TextArea();
    private final TextArea logArea = new TextArea();

    private final Button kindButton = new Button("Gentile");
    private final Button passiveButton = new Button("Passivo-aggressiva");
    private final Button rudeButton = new Button("Maleducata");
    private final Button newGameButton = new Button("Nuova partita");
    private final Button resumeButton = new Button("Torna al menù");
    private final Button restartButton = new Button("Riavvia partita");

    private final VBox menuPane = new VBox(16);
    private final VBox gamePane = new VBox(12);

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
        root.setStyle(ROOT_STYLE);

        titleLabel.setStyle(TITLE_STYLE);
        levelLabel.setStyle(TEXT_STYLE);
        timeLabel.setStyle(TEXT_STYLE);

        dialogueArea.setEditable(false);
        dialogueArea.setWrapText(true);
        dialogueArea.setStyle(TEXT_AREA_STYLE);

        logArea.setEditable(false);
        logArea.setWrapText(true);
        logArea.setStyle(TEXT_AREA_STYLE);

        VBox playerBox = new VBox(8,
                new Label("Statistiche giocatore"),
                playerNameLabel,
                hpLabel,
                patienceLabel
        );
        playerBox.setStyle(PANEL_STYLE);

        VBox enemyBox = new VBox(8,
                new Label("Statistiche nemico"),
                enemyNameLabel,
                authorityLabel,
                irritationLabel
        );
        enemyBox.setStyle(PANEL_STYLE);

        HBox statsBox = new HBox(24, playerBox, enemyBox);
        statsBox.setPadding(new Insets(16));

        VBox topBox = new VBox(8, titleLabel, levelLabel, timeLabel);
        topBox.setPadding(new Insets(16));

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

        HBox controlButtons = new HBox(10, newGameButton, resumeButton, restartButton);
        controlButtons.setAlignment(Pos.CENTER_LEFT);
        controlButtons.setPadding(new Insets(16, 16, 0, 16));

        gamePane.getChildren().addAll(controlButtons, topBox, statsBox, centerBox, actionButtons);

        menuPane.setAlignment(Pos.CENTER);
        menuPane.setPadding(new Insets(24));

        Label menuTitle = new Label("Soon Unemployed");
        menuTitle.setStyle(TITLE_STYLE);

        Label menuText = new Label(
                "Soon Unemployed è un gioco di ruolo a turni ambientato nel contesto lavorativo contemporaneo.\n\n" +
                "Devi superare tre incontri consecutivi nell'arco della giornata di venerdì, gestendo salute mentale, pazienza e rischio di licenziamento.\n\n" +
                "Scegli Nuova partita per iniziare oppure Carica partita se hai già un salvataggio."
        );
        menuText.setStyle(MENU_TEXT_STYLE);
        menuText.setWrapText(true);
        menuText.setMaxWidth(520);

        Button loadButton = new Button("Carica partita");
        loadButton.setStyle(SECONDARY_BUTTON_STYLE);
        newGameButton.setStyle(PRIMARY_BUTTON_STYLE);
        resumeButton.setStyle(NEUTRAL_BUTTON_STYLE);
        restartButton.setStyle(NEUTRAL_BUTTON_STYLE);
        kindButton.setStyle(SECONDARY_BUTTON_STYLE);
        passiveButton.setStyle(SECONDARY_BUTTON_STYLE);
        rudeButton.setStyle(PRIMARY_BUTTON_STYLE);

        loadButton.setOnAction(event -> {
            String playerName = askPlayerName();
            if (playerName == null) {
                return;
            }
            session.loadOrStartNew(playerName);
            showGame();
            refresh();
            appendLog("Partita caricata.");
        });

        menuPane.getChildren().addAll(menuTitle, menuText, newGameButton, loadButton);
    }

    private void bindActions() {
        kindButton.setOnAction(event -> handleAction(BattleAction.BE_KIND));
        passiveButton.setOnAction(event -> handleAction(BattleAction.PASSIVE_AGGRESSIVE));
        rudeButton.setOnAction(event -> handleAction(BattleAction.RUDE));

        newGameButton.setOnAction(event -> {
            String playerName = askPlayerName();
            if (playerName == null) {
                return;
            }
            session.restartGame(playerName);
            showGame();
            refresh();
            appendLog("Nuova partita avviata.");
        });

        resumeButton.setOnAction(event -> {
            session.deleteSave();
            session.clearSession();
            showMenu();
        });

        restartButton.setOnAction(event -> {
            String playerName = askPlayerName();
            if (playerName == null) {
                return;
            }
            session.restartGame(playerName);
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
            clearGameFields();
            return;
        }

        levelLabel.setText("Livello: " + state.getLevel());
        timeLabel.setText("Orario: " + state.getTimeLabel());

        playerNameLabel.setText("Giocatore: " + state.getPlayer().getName());
        hpLabel.setText("HP mentale: " + state.getPlayer().getStats().getMentalHealth());
        patienceLabel.setText("Pazienza: " + state.getPlayer().getStats().getPatience());

        enemyNameLabel.setText("Nemico: " + state.getCurrentEnemy().getName());
        authorityLabel.setText("Autorita nemico: " + state.getCurrentEnemy().getAuthority());
        irritationLabel.setText("Irritazione: " + state.getCurrentEnemy().getIrritation());
    }

    private void showMenu() {
        root.setCenter(menuPane);
        clearGameFields();
        disableButtons();
    }

    private void showGame() {
        root.setCenter(gamePane);
        kindButton.setDisable(false);
        passiveButton.setDisable(false);
        rudeButton.setDisable(false);
        resumeButton.setDisable(false);
        restartButton.setDisable(false);
    }

    private String askPlayerName() {
        TextInputDialog dialog = new TextInputDialog("John");
        dialog.setTitle("Nuova partita");
        dialog.setHeaderText("Inserisci il nome del giocatore");
        dialog.setContentText("Nome:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return null;
        }

        String playerName = result.get().trim();
        return playerName.isEmpty() ? "John" : playerName;
    }

    private void clearGameFields() {
        levelLabel.setText("");
        timeLabel.setText("");
        playerNameLabel.setText("");
        hpLabel.setText("");
        patienceLabel.setText("");
        enemyNameLabel.setText("");
        authorityLabel.setText("");
        irritationLabel.setText("");
        dialogueArea.clear();
        logArea.clear();
    }

    private void appendDialogue(BattleAction action) {
        switch (action) {
            case BE_KIND:
                appendDialogueLine("Tu: Certamente, me ne occupo subito.");
                appendDialogueLine("Collega: Bene, contavo proprio su di te.");
                appendDialogueLine("Tu: Ti aggiorno appena ho finito.");
                appendDialogueLine("Collega: Perfetto, grazie.");
                break;
            case PASSIVE_AGGRESSIVE:
                appendDialogueLine("Tu: Come da mia precedente mail...");
                appendDialogueLine("Collega: Sì, ma la situazione è urgente.");
                appendDialogueLine("Tu: Infatti sto già valutando la priorità.");
                appendDialogueLine("Collega: Va bene, aspetto un riscontro.");
                break;
            case RUDE:
                appendDialogueLine("Tu: No, non lo faccio. Arrangiati.");
                appendDialogueLine("Collega: Come ti permetti?");
                appendDialogueLine("Tu: Ho altre priorità oggi.");
                appendDialogueLine("Collega: Questo comportamento avrà conseguenze.");
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
        resumeButton.setDisable(true);
        restartButton.setDisable(true);
    }
}