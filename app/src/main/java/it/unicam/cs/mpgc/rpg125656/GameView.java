package it.unicam.cs.mpgc.rpg125656;

import it.unicam.cs.mpgc.rpg125656.entity.BattleAction;
import it.unicam.cs.mpgc.rpg125656.entity.GameState;

public interface GameView {
    void showHeader();

String askPlayerName();

void showTurnState(GameState state);

BattleAction askAction();

void showActionChosen(BattleAction action);

void showDialogue(BattleAction action);

void showTurnResult(GameState state, int previousLevel, String previousEnemyName);

void showFinalOutcome(GameState state);
}