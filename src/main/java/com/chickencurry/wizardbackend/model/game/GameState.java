package com.chickencurry.wizardbackend.model.game;

import com.chickencurry.wizardbackend.model.card.Card;
import com.chickencurry.wizardbackend.model.card.CardDeck;
import com.chickencurry.wizardbackend.model.card.CardSuit;
import com.chickencurry.wizardbackend.model.player.Player;
import com.chickencurry.wizardbackend.model.player.PlayerCardPair;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class GameState {

    private final int MIN_PLAYER_COUNT = 3;
    private final int MAX_PLAYER_COUNT = 6;

    private final String gameId;
    private final GameScore gameScore;
    private final CardDeck deck;
    private GameStage currentStage;
    private int currentRound;
    private CardSuit currentTrumpSuit;
    private CardSuit currentDesiredSuit;
    private Player currentTurnPlayer;
    private final Stack<PlayerCardPair> playedCards;
    private final LinkedList<Player> players; // last player is dealer
    private Player host;
    private List<Player> winners;
    private boolean isReady;
    private boolean trumpSuitChosen;
    private boolean cardPlayed;
    private boolean tricksPredicted;

    public GameState(String gameId, List<Player> players) {
        this.gameId = gameId;
        this.players = new LinkedList<>(players);
        this.gameScore = new GameScore(players);
        this.host = players.get(0);
        this.currentTurnPlayer = players.get(0);

        this.deck = new CardDeck();
        this.playedCards = new Stack<>();
        this.currentRound = 0;
        this.currentStage = null;
        this.currentTrumpSuit = null;
        this.currentDesiredSuit = null;
        this.winners = null;
        this.trumpSuitChosen = false;
        this.cardPlayed = false;
        this.tricksPredicted = false;
        this.isReady = false;
    }

    public GameScore getGameScore() {
        return gameScore;
    }

    public CardSuit getCurrentDesiredSuit() {
        return currentDesiredSuit;
    }

    public void setCurrentDesiredSuit(CardSuit currentDesiredSuit) {
        this.currentDesiredSuit = currentDesiredSuit;
    }

    public GameStage getCurrentGameStage() {
        return currentStage;
    }

    public void setCurrentGameStage(GameStage currentStage) {
        this.currentStage = currentStage;
    }

    public Player getHost() {
        return host;
    }

    public void setHost(Player host) {
        this.host = host;
    }

    public void addPlayer(Player player) {
        players.add(player);
        isReady = MIN_PLAYER_COUNT <= players.size() && players.size() <= MAX_PLAYER_COUNT;
    }

    public void removePlayer(String playerId) {
        players.removeIf(player -> player.getPlayerId().equals(playerId));
        isReady = MIN_PLAYER_COUNT <= players.size() && players.size() <= MAX_PLAYER_COUNT;
    }

    public void updatePlayer(String playerId, Player player) {
        players.removeIf(p -> p.getPlayerId().equals(playerId));
        players.add(player);
        isReady = MIN_PLAYER_COUNT <= players.size() && players.size() <= MAX_PLAYER_COUNT;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void resetDeck() {
        deck.resetDeck();
    }

    public Card popDeck() {
        return deck.popDeck();
    }

    public boolean isDeckEmpty() {
        return deck.isDeckEmpty();
    }

    public void resetPlayedCards() {
        playedCards.clear();
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public void rotateDealer() {
        Player dealer = players.removeLast();
        players.addFirst(dealer);
    }

    public void playCard(Card card) {
        playedCards.push(new PlayerCardPair(currentTurnPlayer, card));
        currentTurnPlayer.removeFromHand(card);
    }

    public Player getCurrentTurnPlayer() {
        return currentTurnPlayer;
    }

    public void setCurrentTurnPlayer(Player currentTurnPlayer) {
        this.currentTurnPlayer = currentTurnPlayer;
    }

    public List<Player> getWinners() {
        return winners;
    }

    public void setWinners(List<Player> winners) {
        this.winners = winners;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public CardSuit getCurrentTrumpSuit() {
        return currentTrumpSuit;
    }

    public void setCurrentTrumpSuit(CardSuit currentTrumpSuit) {
        this.currentTrumpSuit = currentTrumpSuit;
    }

    public CardDeck getDeck() {
        return deck;
    }

    public Stack<PlayerCardPair> getPlayedCards() {
        return playedCards;
    }

    public boolean isReady() {
        return isReady;
    }

    public boolean isTrumpSuitChosen() {
        return trumpSuitChosen;
    }

    public void setTrumpSuitChosen(boolean trumpSuitChosen) {
        this.trumpSuitChosen = trumpSuitChosen;
    }

    public boolean isCardPlayed() {
        return cardPlayed;
    }

    public void setCardPlayed(boolean cardPlayed) {
        this.cardPlayed = cardPlayed;
    }

    public boolean isTricksPredicted() {
        return tricksPredicted;
    }

    public void setTricksPredicted(boolean tricksPredicted) {
        this.tricksPredicted = tricksPredicted;
    }
}
