package com.chickencurry.wizardbackend.model.card;

public record Card(CardType type, CardSuit suit) {

    public boolean isJester() {
        return type == CardType.JESTER;
    }

    public boolean isWizard() {
        return type == CardType.WIZARD;
    }

    public boolean isNumberCard() {
        return type != CardType.JESTER && type != CardType.WIZARD;
    }

    public int getNumber() {
        return type.getNumber();
    }

}
