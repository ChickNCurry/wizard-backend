package com.chickencurry.wizardbackend.model.player;

import com.chickencurry.wizardbackend.model.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private final String id;
    private final ArrayList<Card> hand;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
