package com.chickencurry.wizardbackend.model.game;

import com.chickencurry.wizardbackend.model.card.Card;
import com.chickencurry.wizardbackend.model.card.CardSuit;

public class GameAction {
    private String playerId;
    private GameActionType actionType;
    private Card card;
    private CardSuit suit;
    private int tricks;

}
