package com.chickencurry.wizardbackend.model.card;

import java.util.Stack;

public class CardDeck {

    private Stack<Card> deck;

    public CardDeck() {
        resetDeck();
    }

    public void resetDeck() {
        deck = shuffleDeck(getFreshDeck());
    }

    private Stack<Card> getFreshDeck() {
        Stack<Card> deck = new Stack<>();
        for(CardSuit suit : CardSuit.values()) {
            for(CardType type : CardType.values()){
                deck.push(new Card(type, suit));
            }
        }
        return deck;
    }

    private Stack<Card> shuffleDeck(Stack<Card> deck) {
        for(int i = deck.size() - 1; i > 0; i--) {
            int newIndex = (int) Math.floor(Math.random() * (i + 1));
            Card oldCard = deck.elementAt(newIndex);
            deck.set(newIndex, deck.elementAt(i));
            deck.set(i, oldCard);
        }
        return deck;
    }

    public int getDeckSize() {
        return deck.size();
    }

    public boolean isDeckEmpty() { return deck.empty(); }

    public Card popDeck() {
        return deck.pop();
    }

}
