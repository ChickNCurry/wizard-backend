package com.chickencurry.wizardbackend.model.player;

import com.chickencurry.wizardbackend.model.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String playerName;
    private final String playerId;
    private final ArrayList<Card> hand;

    public Player(String userId, String userName) {
        this.playerId = userId;
        this.playerName = userName;
        this.hand = new ArrayList<>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addToHand(Card card) {
        hand.add(card);
    }

    public void removeFromHand(Card card) {
        hand.remove(card);
    }

    public boolean hasInHand(Card card) {
        return hand.contains(card);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerId() {
        return playerId;
    }

}
