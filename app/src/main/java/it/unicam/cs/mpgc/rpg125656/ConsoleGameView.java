package it.unicam.cs.mpgc.rpg125656;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;

import java.util.Scanner;

public class ConsoleGameView implements GameView {
    private final Scanner scanner = new Scanner(System.in);

@Override
public void showHeader() {
    System.out.println("====================================");
    System.out.println("          SOON UNEMPLOYED           ");
    System.out.println("====================================");
    System.out.println("Gestisci le tue energie e supera i tre incontri.");
    System.out.println();
}

@Override
public String askPlayerName() {
    System.out.print("Inserisci il tuo nome: ");
    String playerName = scanner.nextLine().trim();
    return playerName.isEmpty() ? "John" : playerName;
}

@Override
public void showTurnState(GameState state) {
    System.out.println();
    System.out.println("------------- STATO -------------");
    System.out.println("Livello: " + state.getLevel());
    System.out.println("Orario: " + state.getTimeLabel());
    System.out.println("Giocatore: " + state.getPlayer().getName());
    System.out.println("HP mentale: " + state.getPlayer().getStats().getMentalHealth());
    System.out.println("Pazienza: " + state.getPlayer().getStats().getPatience());
    System.out.println("Nemico: " + state.getCurrentEnemy().getName());
    System.out.println("Autorita nemico: " + state.getCurrentEnemy().getAuthority());
    System.out.println("Irritazione nemico: " + state.getCurrentEnemy().getIrritation());
    System.out.println("---------------------------------");
    System.out.println("Scegli un'azione:");
    System.out.println("1) Gentile");
    System.out.println("2) Passivo-aggressiva");
    System.out.println("3) Maleducata");
}

@Override
public BattleAction askAction() {
    while (true) {
        System.out.print("> ");
        String input = scanner.nextLine().trim();

        switch (input) {
            case "1":
                return BattleAction.BE_KIND;
            case "2":
                return BattleAction.PASSIVE_AGGRESSIVE;
            case "3":
                return BattleAction.RUDE;
            default:
                System.out.println("Scelta non valida. Inserisci 1, 2 o 3.");
        }
    }
}

@Override
public void showActionChosen(BattleAction action) {
    System.out.println("Hai scelto: " + action.getLabel());
}

@Override
public void showDialogue(BattleAction action) {
    System.out.println();
    System.out.println("Dialogo:");

    switch (action) {
        case BE_KIND:
            typeText("Nemico: Ho una richiesta urgente. La prendi in carico tu o devo cercare un altro modo?");
            pause(150);
            typeText("Tu: Va bene, mandami tutto e mi occupo io della parte urgente.");
            pause(150);
            typeText("Nemico: Bene, vediamo se riesci a chiudere davvero questa cosa.");
            pause(150);
            typeText("Nemico: Puoi anche prendere in carico il resto, così ci togliamo il pensiero?");
            break;
        case PASSIVE_AGGRESSIVE:
            typeText("Nemico: Ho bisogno di una risposta adesso. La fai tu o la devo riassegnare?");
            pause(150);
            typeText("Tu: Strano che l'urgenza arrivi sempre quando serve a me.");
            pause(150);
            typeText("Nemico: Non è il momento per le polemiche.");
            pause(150);
            typeText("Nemico: Quindi, me la sistemi o devo chiedere a qualcun altro?");
            break;
        case RUDE:
            typeText("Nemico: Voglio una soluzione immediata. Sei tu quello che me la porta, oppure no?");
            pause(150);
            typeText("Tu: No, occupatene da solo.");
            pause(150);
            typeText("Nemico: Questo tono non aiuta affatto.");
            pause(150);
            typeText("Nemico: Ultima occasione: me la sistemi o no?");
            break;
    }

    System.out.println();
}

@Override
public void showTurnResult(GameState state, int previousLevel, String previousEnemyName) {
    System.out.println();
    System.out.println("--------- RISULTATO TURNO ---------");

    if (state.getOutcome() == GameState.Outcome.BURNOUT) {
        System.out.println("Hai esaurito le energie.");
    } else if (state.getOutcome() == GameState.Outcome.FIRED) {
        System.out.println("Il boss ti ha licenziato.");
    } else if (state.getOutcome() == GameState.Outcome.VICTORY) {
        System.out.println("Hai superato l'ultimo incontro.");
    } else if (state.getLevel() > previousLevel) {
        System.out.println("Hai sconfitto " + previousEnemyName + " e sei passato al livello " + state.getLevel() + ".");
        System.out.println("Nuovo orario: " + state.getTimeLabel());
        System.out.println("Nuovo nemico: " + state.getCurrentEnemy().getName());
    } else {
        System.out.println("Il turno e' terminato.");
        System.out.println("Nemico attuale: " + state.getCurrentEnemy().getName());
        System.out.println("Autorita rimasta: " + state.getCurrentEnemy().getAuthority());
        System.out.println("Irritazione: " + state.getCurrentEnemy().getIrritation());
    }

    System.out.println("----------------------------------");
}

@Override
public void showFinalOutcome(GameState state) {
    System.out.println();
    System.out.println("============ FINE GIOCO ============");

    switch (state.getOutcome()) {
        case VICTORY:
            System.out.println("Hai vinto la giornata. Bravo.");
            break;
        case BURNOUT:
            System.out.println("Sei arrivato allo sfinimento.");
            break;
        case FIRED:
            System.out.println("Sei stato licenziato.");
            break;
        default:
            System.out.println("Partita terminata.");
            break;
    }

    System.out.println("====================================");
}

private void typeText(String text) {
    for (char character : text.toCharArray()) {
        System.out.print(character);
        System.out.flush();

        try {
            Thread.sleep(18);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return;
        }
    }

    System.out.println();
}

private void pause(long milliseconds) {
    try {
        Thread.sleep(milliseconds);
    } catch (InterruptedException exception) {
        Thread.currentThread().interrupt();
    }
}
}



