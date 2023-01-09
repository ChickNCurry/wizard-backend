package com.chickencurry.wizardbackend.service;

import com.chickencurry.wizardbackend.components.GamesMap;
import com.chickencurry.wizardbackend.model.card.Card;
import com.chickencurry.wizardbackend.model.card.CardSuit;
import com.chickencurry.wizardbackend.model.card.CardType;
import com.chickencurry.wizardbackend.model.game.GameStage;
import com.chickencurry.wizardbackend.model.game.GameState;
import com.chickencurry.wizardbackend.model.player.Player;
import com.chickencurry.wizardbackend.model.player.PlayerCardPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;

@Service
public class GameService {

    private final UserService userService;
    private final GamesMap gamesMap;

    @Autowired
    public GameService(UserService userService, GamesMap gamesMap) {
        this.userService = userService;
        this.gamesMap = gamesMap;
    }

    public List<Player> getPlayers(String gameId) {
        return gamesMap.getGame(gameId).getPlayers();
    }

    public Player getTurnPlayer(String gameId) {
        return gamesMap.getGame(gameId).getCurrentTurnPlayer();
    }

    public void createGame(List<String> userIds) {
        String gameId = gamesMap.createGame(userIds.stream()
                .map(entry -> new Player(entry, userService.getUser(entry).getUserName())).toList());

        new Thread(() -> {
            try {
                runGame(gameId, userIds.get(0));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void deleteGame(String gameId) {
        gamesMap.deleteGame(gameId);
    }

    public synchronized void runGame(String gameId, String playerId) throws InterruptedException {
        GameState game = gamesMap.getGame(gameId);

        if(!playerId.equals(game.getHost().getPlayerId())) return;

        if(!game.isReady()) {
            System.out.println("invalid player count: " + game.getPlayerCount());
            return;
        }

        int rounds = game.getDeck().getDeckSize() / game.getPlayerCount();
        for(int i = 0; i < rounds; i++) {
            game.setCurrentRound(i + 1);
            runDealingStage(gameId);
            runBiddingStage(gameId);
            runPlayingStage(gameId);
            game.rotateDealer();
        }

        game.setWinners(game.getGameScore().determineWinners(game.getPlayers()));
    }

    private synchronized void runDealingStage(String gameId) throws InterruptedException {
        GameState game = gamesMap.getGame(gameId);

        game.setCurrentGameStage(GameStage.DEALING);
        game.resetDeck();

        // distribute cards
        for(int i = 0; i < game.getCurrentRound(); i++) {
            for(Player player : game.getPlayers()) {
                player.addToHand(game.popDeck());
            }
        }

        // set trump suit
        if(!game.isDeckEmpty()) {
            Card card = game.popDeck();
            if(card.type() == CardType.JESTER) {
                game.setCurrentTrumpSuit(null);
            } else if (card.type() == CardType.WIZARD) {
                // wait until trump suit is chosen by dealer
                game.setCurrentTurnPlayer(game.getPlayers().getLast());
                game.setTrumpSuitChosen(false);
                while (!game.isTrumpSuitChosen()) {
                    wait();
                }
                game.setCurrentTurnPlayer(null);
            }
        } else {
            game.setCurrentTrumpSuit(null);
        }
    }

    private synchronized void runBiddingStage(String gameId) throws InterruptedException {
        GameState game = gamesMap.getGame(gameId);

        game.setCurrentGameStage(GameStage.BIDDING);
        game.getGameScore().resetTricks();

        for(Player player: game.getPlayers()) {
            // wait until trick count is chosen by turn player
            game.setCurrentTurnPlayer(player);
            game.setTricksPredicted(false);
            while(!game.isTricksPredicted()) {
                wait();
            }
            game.setCurrentTurnPlayer(null);
        }
    }

    private synchronized void runPlayingStage(String gameId) throws InterruptedException {
        GameState game = gamesMap.getGame(gameId);

        game.setCurrentGameStage(GameStage.PLAYING);
        game.resetPlayedCards();

        for (int i = 0; i < game.getCurrentRound(); i++) {
            game.setCurrentDesiredSuit(null);
            for(Player player: game.getPlayers()) {
                // wait until card is played by turn player
                game.setCurrentTurnPlayer(player);
                game.setCardPlayed(false);
                while(!game.isCardPlayed()) {
                    wait();
                }
                game.setCurrentTurnPlayer(null);
            }
            Player winner = evaluateTrickWinner(gameId);
            game.getGameScore().addToTrickCount(winner);
        }

        game.getGameScore().recordScores(game.getPlayers());
    }

    public synchronized void chooseTrumpSuit(String gameId, String playerId, CardSuit suit) {
        GameState game = gamesMap.getGame(gameId);

        if(game.getCurrentGameStage() == GameStage.DEALING
                && playerId.equals(game.getCurrentTurnPlayer().getPlayerId())) {
            game.setCurrentTrumpSuit(suit);
            game.setTrumpSuitChosen(true);
            notifyAll();
        }
    }

    public synchronized void predictTricks(String gameId, String playerId, int tricks) {
        GameState game = gamesMap.getGame(gameId);

        if(game.getCurrentGameStage() == GameStage.BIDDING
                && playerId.equals(game.getCurrentTurnPlayer().getPlayerId())) {
            game.getGameScore().recordTrickPrediction(game.getCurrentTurnPlayer(), tricks);
            game.setTricksPredicted(true);
            notifyAll();
        }
    }

    public synchronized void playCard(String gameId, String playerId, Card card) {
        GameState game = gamesMap.getGame(gameId);

        if(game.getCurrentGameStage() == GameStage.PLAYING
                && playerId.equals(game.getCurrentTurnPlayer().getPlayerId())) {

            if(game.getCurrentTurnPlayer().hasInHand(card) && isCardPlayable(gameId, card)) {

                game.playCard(card);

                // if desired suit not yet set and current card is number card
                if(game.getCurrentDesiredSuit() != null && card.isNumberCard()) {

                    // if current card is first then set desired suit
                    if(game.getPlayedCards().empty()) {
                        game.setCurrentDesiredSuit(card.suit());
                    }
                    //if jester was first and wizard was not second
                    if(game.getPlayedCards().size() > 1
                            && game.getPlayedCards().get(0).card().isJester()
                            && !game.getPlayedCards().get(1).card().isWizard()) {
                        game.setCurrentDesiredSuit(card.suit());
                    }
                }

                game.setCardPlayed(true);
                notifyAll();
            }
        }
    }

    public boolean isCardPlayable(String gameId, Card card) {
        GameState game = gamesMap.getGame(gameId);

        if(card.type() == CardType.JESTER || card.type() == CardType.WIZARD) return true;
        else if(game.getCurrentDesiredSuit() == null) return true;
        else if(game.getCurrentDesiredSuit() == card.suit()) return true;
        else {
            for(Card c: game.getCurrentTurnPlayer().getHand()) {
                if (c.suit() == game.getCurrentDesiredSuit()) return false;
            }
        }
        return true;
    }

    private Player evaluateTrickWinner(String gameId) {
        GameState game = gamesMap.getGame(gameId);

        Stack<PlayerCardPair> playedCards = game.getPlayedCards();
        PlayerCardPair trickWinner = null;

        while(!playedCards.isEmpty()) {
            PlayerCardPair top = playedCards.pop();

            if(top.card().isWizard()) {
                trickWinner = top;
            }
            else if (trickWinner == null) {
                trickWinner = top;
            }
            else if(top.card().isNumberCard()) {
                if(top.card().suit() == game.getCurrentTrumpSuit()) {
                    if(trickWinner.card().suit() == game.getCurrentTrumpSuit()) {
                        if(top.card().getNumber() > trickWinner.card().getNumber()) {
                            trickWinner = top;
                        }
                    } else {
                        trickWinner = top;
                    }
                }
                else if (top.card().suit() == game.getCurrentDesiredSuit()) {
                    if(trickWinner.card().suit() != game.getCurrentTrumpSuit()) {
                        if(trickWinner.card().suit() == game.getCurrentDesiredSuit()) {
                            if(top.card().getNumber() > trickWinner.card().getNumber()) {
                                trickWinner = top;
                            }
                        } else {
                            trickWinner = top;
                        }
                    }
                }
                else if (trickWinner.card().suit() != game.getCurrentTrumpSuit()
                        && trickWinner.card().suit() != game.getCurrentDesiredSuit()
                        && top.card().getNumber() > trickWinner.card().getNumber()) {
                    trickWinner = top;
                }
            }
        }

        if(trickWinner == null) {
            trickWinner = game.getPlayedCards().firstElement();
        }

        return trickWinner.player();
    }

    public List<Player> getWinners(String gameId) {
        GameState game = gamesMap.getGame(gameId);
        return game.getWinners();
    }

}
