package com.chickencurry.wizardbackend.model.game;

import com.chickencurry.wizardbackend.model.card.Card;
import com.chickencurry.wizardbackend.model.card.CardSuit;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GameAction {
    private String gameId;
    private String playerId;
    private GameActionType actionType;
    private Card card;
    private CardSuit cardSuit;
    private int tricks;
}
