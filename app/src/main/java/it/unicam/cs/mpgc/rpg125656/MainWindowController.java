package it.unicam.cs.mpgc.rpg125656;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class MainWindowController {

    private static final String ROOT_STYLE = "-fx-background-color: #1A1F26;";
    private static final String TITLE_STYLE = "-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #E8EDF2;";
    private static final String TEXT_STYLE = "-fx-text-fill: #C5CED8;";
    private static final String PANEL_STYLE = "-fx-border-color: #3D4A57; -fx-border-width: 1; -fx-padding: 12; -fx-background-color: #242B34;";
    private static final String MENU_TEXT_STYLE = "-fx-text-fill: #B8C4D0; -fx-font-size: 14px;";
    private static final String TEXT_AREA_STYLE = "-fx-control-inner-background: #2A323C; -fx-background-color: #2A323C; -fx-text-fill: #E8EDF2;";
    private static final String PRIMARY_BUTTON_STYLE = "-fx-background-color: #8B3A3A; -fx-text-fill: #F5F5F5;";
    private static final String SECONDARY_BUTTON_STYLE = "-fx-background-color: #3E5C76; -fx-text-fill: #F5F5F5;";
    private static final String NEUTRAL_BUTTON_STYLE = "-fx-background-color: #4A5568; -fx-text-fill: #E8EDF2;";

    private final GameSession session;

    private final BorderPane root = new BorderPane();
    private final Label titleLabel = new Label("Soon Unemployed");
    private final Label levelLabel = new Label();
    private final Label timeLabel = new Label();

    private final ProgressBar hpBar = new ProgressBar(0);
    private final Label hpBarLabel = new Label();
    private final StackPane hpStack = new StackPane();

    private final ProgressBar patienceBar = new ProgressBar(0);
    private final Label patienceBarLabel = new Label();
    private final StackPane patienceStack = new StackPane();

    private final Label enemyNameLabel = new Label();
    private final ProgressBar enemyAuthorityBar = new ProgressBar(0);
    private final Label enemyAuthorityLabel = new Label();
    private final StackPane enemyAuthorityStack = new StackPane();

    private final ProgressBar enemyIrritationBar = new ProgressBar(0);
    private final Label enemyIrritationLabel = new Label();
    private final StackPane enemyIrritationStack = new StackPane();

    private final TextArea dialogueArea = new TextArea();
    private final TextArea logArea = new TextArea();

    private final Button kindButton = new Button("Gentile");
    private final Button passiveButton = new Button("Passivo-aggressiva");
    private final Button rudeButton = new Button("Maleducata");
    private final Button newGameButton = new Button("Nuova partita");
    private final Button saveButton = new Button("Salva partita");
    private final Button resumeButton = new Button("Torna al menù");
    private final Button restartButton = new Button("Riavvia partita");
    private final Button nextLevelButton = new Button("Prossimo livello");

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

        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(12);
        statsGrid.setVgap(8);
        statsGrid.setPadding(new Insets(12));
        statsGrid.setStyle(PANEL_STYLE);

        ColumnConstraints labelCol = new ColumnConstraints();
        labelCol.setMinWidth(100);
        labelCol.setMaxWidth(140);
        labelCol.setHgrow(Priority.NEVER);

        ColumnConstraints barCol = new ColumnConstraints();
        barCol.setHgrow(Priority.ALWAYS);
        barCol.setFillWidth(true);
        barCol.setMinWidth(160);

        ColumnConstraints valueCol = new ColumnConstraints();
        valueCol.setMinWidth(60);
        valueCol.setMaxWidth(80);
        valueCol.setHgrow(Priority.NEVER);

        ColumnConstraints spacerCol = new ColumnConstraints();
        spacerCol.setMinWidth(20);
        spacerCol.setHgrow(Priority.NEVER);

        statsGrid.getColumnConstraints().addAll(labelCol, barCol, valueCol, spacerCol, labelCol, barCol, valueCol);

        Label playerTitle = new Label("Statistiche giocatore");
        playerTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #E8EDF2;");
        statsGrid.add(playerTitle, 0, 0, 3, 1);

        Label enemyTitle = new Label("Statistiche nemico");
        enemyTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #E8EDF2;");
        statsGrid.add(enemyTitle, 4, 0, 3, 1);

        Label hpText = new Label("Salute mentale:");
        hpText.setStyle(TEXT_STYLE);
        hpBar.setProgress(0);
        hpBar.setStyle("-fx-accent: #2E7D4F;");
        hpBarLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        hpStack.getChildren().addAll(hpBar, hpBarLabel);
        StackPane.setAlignment(hpBarLabel, Pos.CENTER);
        statsGrid.add(hpText, 0, 1);
        statsGrid.add(hpStack, 1, 1);
        statsGrid.add(new Label(""), 2, 1);

        Label authorityText = new Label("Autorità:");
        authorityText.setStyle(TEXT_STYLE);
        enemyAuthorityBar.setProgress(0);
        enemyAuthorityBar.setStyle("-fx-accent: #C67C1E;");
        enemyAuthorityLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        enemyAuthorityStack.getChildren().addAll(enemyAuthorityBar, enemyAuthorityLabel);
        StackPane.setAlignment(enemyAuthorityLabel, Pos.CENTER);
        statsGrid.add(authorityText, 4, 1);
        statsGrid.add(enemyAuthorityStack, 5, 1);
        statsGrid.add(new Label(""), 6, 1);

        Label patienceText = new Label("Pazienza:");
        patienceText.setStyle(TEXT_STYLE);
        patienceBar.setProgress(0);
        patienceBar.setStyle("-fx-accent: #2B6CB0;");
        patienceBarLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        patienceStack.getChildren().addAll(patienceBar, patienceBarLabel);
        StackPane.setAlignment(patienceBarLabel, Pos.CENTER);
        statsGrid.add(patienceText, 0, 2);
        statsGrid.add(patienceStack, 1, 2);
        statsGrid.add(new Label(""), 2, 2);

        Label irritationText = new Label("Irritazione:");
        irritationText.setStyle(TEXT_STYLE);
        enemyIrritationBar.setProgress(0);
        enemyIrritationBar.setStyle("-fx-accent: #7B3F8F;");
        enemyIrritationLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        enemyIrritationStack.getChildren().addAll(enemyIrritationBar, enemyIrritationLabel);
        StackPane.setAlignment(enemyIrritationLabel, Pos.CENTER);
        statsGrid.add(irritationText, 4, 2);
        statsGrid.add(enemyIrritationStack, 5, 2);
        statsGrid.add(new Label(""), 6, 2);

        enemyNameLabel.setStyle(TEXT_STYLE);
        statsGrid.add(enemyNameLabel, 4, 3, 3, 1);

        VBox topBox = new VBox(8, titleLabel, levelLabel, timeLabel);
        topBox.setPadding(new Insets(16));

        Label dialogueTitle = new Label("Dialogo");
        dialogueTitle.setStyle(TEXT_STYLE);
        Label logTitle = new Label("Log");
        logTitle.setStyle(TEXT_STYLE);

        VBox centerBox = new VBox(12,
                dialogueTitle,
                dialogueArea,
                logTitle,
                logArea
        );
        centerBox.setPadding(new Insets(16));

        HBox actionButtons = new HBox(10, kindButton, passiveButton, rudeButton);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setPadding(new Insets(16));

        nextLevelButton.setStyle(PRIMARY_BUTTON_STYLE);
        nextLevelButton.setVisible(false);
        nextLevelButton.setManaged(false);

        HBox controlButtons = new HBox(10, newGameButton, saveButton, resumeButton, restartButton, nextLevelButton);
        controlButtons.setAlignment(Pos.CENTER_LEFT);
        controlButtons.setPadding(new Insets(16, 16, 0, 16));

        gamePane.getChildren().addAll(controlButtons, topBox, statsGrid, centerBox, actionButtons);

        menuPane.setAlignment(Pos.CENTER);
        menuPane.setPadding(new Insets(24));

        Label menuTitle = new Label("Soon Unemployed");
        menuTitle.setStyle(TITLE_STYLE);

        Label menuText = new Label(
                "Soon Unemployed è un gioco di ruolo a turni ambientato nel contesto lavorativo contemporaneo.\n\n" +
                        "Devi superare tre incontri consecutivi nell'arco della giornata di venerdì, gestendo salute mentale, pazienza e rischio di licenziamento.\n\n" +
                        "Le risposte disponibili sono tre: Gentile, Passivo-aggressiva e Maleducata. " +
                        "Ogni scelta cambia in modo diverso la pazienza del giocatore, la salute mentale e l'irritazione del nemico.\n\n" +
                        "La risposta gentile è più sicura, la passivo-aggressiva costa di più ma irrita il nemico, la maleducata è la più rischiosa ma può essere utile in certe situazioni.\n\n" +
                        "Scegli Nuova partita per iniziare oppure Carica partita se hai già un salvataggio."
        );
        menuText.setStyle(MENU_TEXT_STYLE);
        menuText.setWrapText(true);
        menuText.setMaxWidth(520);

        Button loadButton = new Button("Carica partita");
        loadButton.setStyle(SECONDARY_BUTTON_STYLE);
        newGameButton.setStyle(PRIMARY_BUTTON_STYLE);
        saveButton.setStyle(SECONDARY_BUTTON_STYLE);
        resumeButton.setStyle(NEUTRAL_BUTTON_STYLE);
        restartButton.setStyle(NEUTRAL_BUTTON_STYLE);
        kindButton.setStyle(SECONDARY_BUTTON_STYLE);
        passiveButton.setStyle(SECONDARY_BUTTON_STYLE);
        rudeButton.setStyle(PRIMARY_BUTTON_STYLE);

        loadButton.setOnAction(event -> {
            if (!session.hasSave()) {
                showNoSaveDialog();
                return;
            }

            if (!session.loadGame()) {
                showCorruptSaveDialog();
                return;
            }

            showGame();
            refresh();
            if (session.isGameOver()) {
                appendLog("Partita caricata: la sessione era già terminata.");
                disableActionButtons();
            } else {
                showLevelIntro();
                appendLog("Partita caricata.");
            }
        });

        nextLevelButton.setOnAction(event -> {
            nextLevelButton.setVisible(false);
            nextLevelButton.setManaged(false);
            kindButton.setDisable(false);
            passiveButton.setDisable(false);
            rudeButton.setDisable(false);

            showLevelIntro();
            refresh();
            GameState state = session.getState();
            if (state != null) {
                appendLog("Passato al livello " + state.getLevel() + ".");
            }
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
            showLevelIntro();
            appendLog("Nuova partita avviata.");
        });

        saveButton.setOnAction(event -> {
            if (session.getState() == null) {
                showSaveErrorDialog("Nessuna partita attiva da salvare.");
                return;
            }

            if (session.saveGame()) {
                appendLog("Partita salvata.");
            } else {
                showSaveErrorDialog("Impossibile salvare la partita.");
            }
        });

        resumeButton.setOnAction(event -> {
            session.clearSession();
            showMenu();
        });

        restartButton.setOnAction(event -> {
            GameState currentState = session.getState();
            String playerName = currentState != null ? currentState.getPlayer().getName() : askPlayerName();
            if (playerName == null) {
                return;
            }
            session.restartGame(playerName);
            showGame();
            refresh();
            showLevelIntro();
            appendLog("Partita riavviata.");
        });
    }

    /**
     * Handles one player turn in the UI: shows dialogue, runs the action in the session,
     * refreshes bars, and enables "next level" when the enemy is defeated but the game continues.
     */
    private void handleAction(BattleAction action) {
        GameState state = session.getState();
        if (state == null || session.isGameOver()) {
            return;
        }

        if (action == BattleAction.PASSIVE_AGGRESSIVE && state.getPlayer().getStats().getPatience() <= 0) {
            appendLog("Non hai abbastanza pazienza per usare una risposta passivo-aggressiva.");
            return;
        }

        int previousLevel = state.getLevel();
        String previousEnemyName = state.getCurrentEnemy().getName();

        appendDialogueLine(getPlayerResponse(previousLevel, action));

        session.applyAction(action);
        refresh();

        boolean enemyDefeated = state.getLevel() > previousLevel || state.getOutcome() == GameState.Outcome.VICTORY;
        if (enemyDefeated) {
            appendDialogueLine(previousEnemyName + ": Va bene, ci vediamo.");
        } else {
            appendDialogueLine(getEnemyReaction(previousLevel, action));
            appendDialogueLine(getEnemyFollowUp(previousLevel, action));
        }

        appendTurnResult(state, previousLevel, previousEnemyName);

        if (!session.isGameOver() && state.getLevel() > previousLevel) {
            kindButton.setDisable(true);
            passiveButton.setDisable(true);
            rudeButton.setDisable(true);
            nextLevelButton.setVisible(true);
            nextLevelButton.setManaged(true);
        }

        if (session.isGameOver()) {
            appendLog("Partita terminata.");
            disableActionButtons();
            nextLevelButton.setVisible(false);
            nextLevelButton.setManaged(false);
            resumeButton.setDisable(false);
            restartButton.setDisable(false);
        }
    }

    private void refresh() {
        GameState state = session.getState();
        if (state == null) {
            clearGameFields();
            disableActionButtons();
            return;
        }

        levelLabel.setText("Livello: " + state.getLevel());
        timeLabel.setText("Orario: " + state.getTimeLabel());

        int hp = state.getPlayer().getStats().getMentalHealth();
        int patience = state.getPlayer().getStats().getPatience();

        hpBar.setProgress(Math.max(0.0, Math.min(1.0, hp / 100.0)));
        hpBarLabel.setText(hp + "/100");

        patienceBar.setProgress(Math.max(0.0, Math.min(1.0, patience / 50.0)));
        patienceBarLabel.setText(patience + "/50");

        var enemy = state.getCurrentEnemy();
        enemyNameLabel.setText("Nome: " + enemy.getName());

        int authority = enemy.getAuthority();
        int maxAuthority = Math.max(1, enemy.getMaxAuthority());
        enemyAuthorityBar.setProgress(Math.max(0.0, Math.min(1.0, ((double) authority) / maxAuthority)));
        enemyAuthorityLabel.setText(authority + "/" + maxAuthority);

        int irritation = enemy.getIrritation();
        enemyIrritationBar.setProgress(Math.max(0.0, Math.min(1.0, irritation / 100.0)));
        enemyIrritationLabel.setText(irritation + "/100");

        updateActionButtonsState(state);
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
        saveButton.setDisable(false);
        resumeButton.setDisable(false);
        restartButton.setDisable(false);
        nextLevelButton.setVisible(false);
        nextLevelButton.setManaged(false);
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
        hpBar.setProgress(0);
        hpBarLabel.setText("");
        patienceBar.setProgress(0);
        patienceBarLabel.setText("");
        enemyNameLabel.setText("");
        enemyAuthorityBar.setProgress(0);
        enemyAuthorityLabel.setText("");
        enemyIrritationBar.setProgress(0);
        enemyIrritationLabel.setText("");
        dialogueArea.clear();
        logArea.clear();
    }

    private void showLevelIntro() {
        GameState state = session.getState();
        if (state == null) {
            return;
        }

        dialogueArea.clear();

        switch (state.getLevel()) {
            case 1:
                appendDialogueLine("Collega: Ho una consegna urgente da fare. La prendi in carico tu o devo cercare un altro modo?");
                break;
            case 2:
                appendDialogueLine("Project manager: Mi serve il report subito. Lo gestisci tu o devo spostarlo ancora una volta?");
                break;
            case 3:
                appendDialogueLine("Grande capo: Voglio una soluzione immediata. Sei tu quello che me la porta, oppure no?");
                break;
            default:
                appendDialogueLine("Collega: Anche stavolta tocca a te sistemare tutto. Ti va di occupartene?");
                break;
        }
    }

    private String getPlayerResponse(int level, BattleAction action) {
        return switch (level) {
            case 1 -> switch (action) {
                case BE_KIND -> "Tu: Va bene, mandami tutto: me ne occupo io.";
                case PASSIVE_AGGRESSIVE -> "Tu: Certo, come sempre tocca a me quando c'è fretta.";
                case RUDE -> "Tu: No, arrangiati da solo.";
            };
            case 2 -> switch (action) {
                case BE_KIND -> "Tu: Va bene, preparo subito il report.";
                case PASSIVE_AGGRESSIVE -> "Tu: Certo, come se non l'avessi già rimandato abbastanza io.";
                case RUDE -> "Tu: No, non ho tempo per il tuo report.";
            };
            case 3 -> switch (action) {
                case BE_KIND -> "Tu: Sì, ci penso io e ti porto una soluzione.";
                case PASSIVE_AGGRESSIVE -> "Tu: Certo, come se non avessi già abbastanza cose da gestire.";
                case RUDE -> "Tu: No, trovala da solo la soluzione.";
            };
            default -> switch (action) {
                case BE_KIND -> "Tu: Va bene, me ne occupo anche stavolta.";
                case PASSIVE_AGGRESSIVE -> "Tu: Ancora io? Va bene, dimmi cosa serve.";
                case RUDE -> "Tu: No, questa volta arrangiati.";
            };
        };
    }

    private String getEnemyReaction(int level, BattleAction action) {
        return switch (level) {
            case 1 -> switch (action) {
                case BE_KIND -> "Collega: Grazie, sapevo che potevo contare su di te.";
                case PASSIVE_AGGRESSIVE -> "Collega: Capisco il punto, ma adesso ho davvero bisogno di una mano.";
                case RUDE -> "Collega: Così mi lasci nei guai.";
            };
            case 2 -> switch (action) {
                case BE_KIND -> "Project manager: Bene, allora manteniamo il focus e chiudiamo in fretta.";
                case PASSIVE_AGGRESSIVE -> "Project manager: Non è il momento per le polemiche.";
                case RUDE -> "Project manager: Questo tono non aiuta il lavoro.";
            };
            case 3 -> switch (action) {
                case BE_KIND -> "Grande capo: Bene, vediamo se il tuo piano regge la pressione.";
                case PASSIVE_AGGRESSIVE -> "Grande capo: Non ho tempo per recriminazioni.";
                case RUDE -> "Grande capo: Stai superando il limite.";
            };
            default -> switch (action) {
                case BE_KIND -> "Collega: Bene, allora passami i dettagli e procediamo.";
                case PASSIVE_AGGRESSIVE -> "Collega: Va bene, dimmi almeno cosa hai bisogno di sapere.";
                case RUDE -> "Collega: Questa risposta non chiude la questione.";
            };
        };
    }

    private String getEnemyFollowUp(int level, BattleAction action) {
        return switch (level) {
            case 1 -> switch (action) {
                case BE_KIND -> "Collega: Puoi anche prendere in carico l'altra parte, così ci togliamo il pensiero?";
                case PASSIVE_AGGRESSIVE -> "Collega: Quindi, me la sistemi o devo chiedere a qualcun altro?";
                case RUDE -> "Collega: Va bene, ma questa non finisce qui.";
            };
            case 2 -> switch (action) {
                case BE_KIND -> "Project manager: Puoi mandarmi anche un aggiornamento tra poco, così resto allineato?";
                case PASSIVE_AGGRESSIVE -> "Project manager: Allora dimmi cosa ti serve per chiudere, subito.";
                case RUDE -> "Project manager: Riparliamone quando avrai un tono più professionale.";
            };
            case 3 -> switch (action) {
                case BE_KIND -> "Grande capo: Allora dimostrami che puoi portarla a termine davvero.";
                case PASSIVE_AGGRESSIVE -> "Grande capo: Bene, ma adesso voglio una risposta concreta.";
                case RUDE -> "Grande capo: Ultima occasione: me la sistemi o no?";
            };
            default -> switch (action) {
                case BE_KIND -> "Collega: Allora, posso contare su di te anche per il resto?";
                case PASSIVE_AGGRESSIVE -> "Collega: D'accordo, ci mettiamo mano oppure no?";
                case RUDE -> "Collega: Ti lascio ripensarci, ma serve una risposta.";
            };
        };
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
            appendLog("Complimenti, hai sconfitto " + previousEnemyName + " e sei passato al livello " + state.getLevel() + ".");
            appendLog("Attenzione: arriva un nuovo nemico.");
        } else {
            appendLog("Turno completato.");
        }
    }

    private void appendLog(String text) {
        logArea.appendText(text + "\n");
    }

    private void updateActionButtonsState(GameState state) {
        boolean passiveAllowed = state.getPlayer().getStats().getPatience() > 0;
        passiveButton.setDisable(!passiveAllowed);
    }

    private void disableActionButtons() {
        kindButton.setDisable(true);
        passiveButton.setDisable(true);
        rudeButton.setDisable(true);
    }

    private void disableButtons() {
        disableActionButtons();
        saveButton.setDisable(true);
        resumeButton.setDisable(true);
        restartButton.setDisable(true);
        nextLevelButton.setVisible(false);
        nextLevelButton.setManaged(false);
    }

    private void showNoSaveDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Carica partita");
        alert.setHeaderText("Nessun salvataggio disponibile");
        alert.setContentText("Non è stato trovato alcun file di salvataggio. Avvia una nuova partita per iniziare.");
        alert.showAndWait();
    }

    private void showCorruptSaveDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Carica partita");
        alert.setHeaderText("Salvataggio non valido");
        alert.setContentText("Il file di salvataggio è danneggiato o illeggibile. Avvia una nuova partita.");
        alert.showAndWait();
    }

    private void showSaveErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Salva partita");
        alert.setHeaderText("Salvataggio non riuscito");
        alert.setContentText(message);
        alert.showAndWait();
    }
}