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
            showLevelIntro();
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
            showLevelIntro();
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
            showLevelIntro();
            appendLog("Partita riavviata.");
        });
    }

    private void handleAction(BattleAction action) {
        GameState state = session.getState();
        if (state == null || session.isGameOver()) {
            return;
        }

        if (action == BattleAction.PASSIVE_AGGRESSIVE && state.getPlayer().getStats().getPatience() <= 0) {
            appendLog("Non hai abbastanza pazienza per usare una risposta passivo-aggressiva.");
            return;
        }

        dialogueArea.clear();
        appendDialogue(action);

        int previousLevel = state.getLevel();
        String previousEnemyName = state.getCurrentEnemy().getName();

        session.applyAction(action);
        refresh();
        appendTurnResult(state, previousLevel, previousEnemyName);

        if (!session.isGameOver() && state.getLevel() > previousLevel) {
            showLevelIntro();
        }

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
            disableActionButtons();
            return;
        }

        levelLabel.setText("Livello: " + state.getLevel());
        timeLabel.setText("Orario: " + state.getTimeLabel());

        playerNameLabel.setText("Giocatore: " + state.getPlayer().getName());
        hpLabel.setText("Salute mentale: " + state.getPlayer().getStats().getMentalHealth());
        patienceLabel.setText("Pazienza: " + state.getPlayer().getStats().getPatience());

        enemyNameLabel.setText("Nemico: " + state.getCurrentEnemy().getName());
        authorityLabel.setText("Autorita nemico: " + state.getCurrentEnemy().getAuthority());
        irritationLabel.setText("Irritazione: " + state.getCurrentEnemy().getIrritation());

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

    private void showLevelIntro() {
        GameState state = session.getState();
        if (state == null) {
            return;
        }

        dialogueArea.clear();

        switch (state.getLevel()) {
            case 1:
                appendDialogueLine("Collega: Cercavo proprio te, ho una consegna urgente da fare. Potresti farla tu? Io proprio non riesco.");
                appendDialogueLine("Collega: Mi servirebbe subito, è una cosa piccolissima.");
                break;
            case 2:
                appendDialogueLine("Project manager: Servono risultati immediati. Ho bisogno che tu ti occupi di questo report senza perdere tempo.");
                appendDialogueLine("Project manager: Se tutto va bene, forse eviteremo altri problemi.");
                break;
            case 3:
                appendDialogueLine("Grande capo: Adesso basta rimandare. Voglio una soluzione subito, senza scuse.");
                appendDialogueLine("Grande capo: Questa è l'ultima prova, dimmi se sei all'altezza.");
                break;
            default:
                appendDialogueLine("Collega: Anche stavolta tocca a te sistemare tutto.");
                break;
        }
    }

    private void appendDialogue(BattleAction action) {
        GameState state = session.getState();
        if (state == null) {
            return;
        }

        switch (state.getLevel()) {
            case 1:
                appendDialogueLine("Collega: Cercavo proprio te, ho una consegna urgente da fare. Potresti farla tu? Io proprio non riesco.");
                switch (action) {
                    case BE_KIND:
                        appendDialogueLine("Tu: Va bene, ma mandami tutto chiaro così la gestisco subito.");
                        appendDialogueLine("Collega: Certo, ti invio tutto adesso.");
                        appendDialogueLine("Tu: Perfetto, così evitiamo altri intoppi.");
                        appendDialogueLine("Collega: Grazie, mi togli un peso.");
                        break;
                    case PASSIVE_AGGRESSIVE:
                        appendDialogueLine("Tu: Strano, le urgenze arrivano sempre quando passi da me.");
                        appendDialogueLine("Collega: Dai, questa volta è davvero importante.");
                        appendDialogueLine("Tu: Allora mandami tutto ordinato e vediamo se si può fare.");
                        appendDialogueLine("Collega: Va bene, te lo preparo subito.");
                        break;
                    case RUDE:
                        appendDialogueLine("Tu: No, occupatene da solo.");
                        appendDialogueLine("Collega: Così mi lasci nei guai.");
                        appendDialogueLine("Tu: La prossima volta organizzati meglio.");
                        appendDialogueLine("Collega: Questa me la segno.");
                        break;
                }
                break;

            case 2:
                appendDialogueLine("Project manager: Mi serve un aggiornamento immediato sul report. La riunione è tra poco.");
                switch (action) {
                    case BE_KIND:
                        appendDialogueLine("Tu: Ti passo un riepilogo subito, così sei allineato.");
                        appendDialogueLine("Project manager: Perfetto, questo mi aiuta molto.");
                        appendDialogueLine("Tu: Se serve, ti evidenzio anche i punti critici.");
                        appendDialogueLine("Project manager: Ottimo, grazie per la collaborazione.");
                        break;
                    case PASSIVE_AGGRESSIVE:
                        appendDialogueLine("Tu: Curioso che serva tutto all'ultimo minuto, come sempre.");
                        appendDialogueLine("Project manager: Non abbiamo tempo per le polemiche.");
                        appendDialogueLine("Tu: Allora dimmi esattamente cosa manca, così chiudiamo la questione.");
                        appendDialogueLine("Project manager: Te lo scrivo subito.");
                        break;
                    case RUDE:
                        appendDialogueLine("Tu: Non posso rifare tutto all'ultimo minuto.");
                        appendDialogueLine("Project manager: Mi servono risultati, non scuse.");
                        appendDialogueLine("Tu: Allora gestisci meglio le priorità la prossima volta.");
                        appendDialogueLine("Project manager: Questo tono non aiuta.");
                        break;
                }
                break;

            case 3:
                appendDialogueLine("Grande capo: Voglio una soluzione adesso. Niente scuse.");
                switch (action) {
                    case BE_KIND:
                        appendDialogueLine("Tu: Va bene, ti do subito un piano concreto.");
                        appendDialogueLine("Grande capo: Parla, ti ascolto.");
                        appendDialogueLine("Tu: Divido il problema in due fasi e ti aggiorno appena ho un risultato.");
                        appendDialogueLine("Grande capo: Bene, voglio vedere se reggi la pressione.");
                        break;
                    case PASSIVE_AGGRESSIVE:
                        appendDialogueLine("Tu: Certo, dopo tutto il tempo che avevo chiesto.");
                        appendDialogueLine("Grande capo: Non è il momento per queste osservazioni.");
                        appendDialogueLine("Tu: Perfetto, allora concentriamoci sulla soluzione e non sul ritardo.");
                        appendDialogueLine("Grande capo: Fai in fretta.");
                        break;
                    case RUDE:
                        appendDialogueLine("Tu: Se vuoi risultati, smetti di ordinare e inizia a spiegare.");
                        appendDialogueLine("Grande capo: Stai superando il limite.");
                        appendDialogueLine("Tu: Il limite lo supera chi pretende tutto senza spiegare nulla.");
                        appendDialogueLine("Grande capo: Vedremo quanto ti conviene.");
                        break;
                }
                break;

            default:
                appendDialogueLine("Collega: Serve una mano, come al solito.");
                appendDialogueLine("Tu: Dimmi cosa ti serve e vediamo.");
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
        resumeButton.setDisable(true);
        restartButton.setDisable(true);
    }
}