package com.chickencurry.wizardbackend.model.game;

import com.chickencurry.wizardbackend.model.player.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GameScore {

    private final HashMap<String, Integer> trickPredictionMap;
    private final HashMap<String, Integer> trickCountMap;
    private final HashMap<String, Integer> scoreMap;

    public GameScore(List<Player> players) {
        trickPredictionMap = new HashMap<>();
        trickCountMap = new HashMap<>();
        scoreMap = new HashMap<>();
        players.forEach(this::addPlayer);
    }

    public void addPlayer(Player player) {
        trickPredictionMap.put(player.getPlayerId(), 0);
        trickCountMap.put(player.getPlayerId(), 0);
        scoreMap.put(player.getPlayerId(), 0);
    }

    public void removePlayer(String playerId) {
        trickPredictionMap.remove(playerId);
        trickCountMap.remove(playerId);
    }

    public void resetTricks() {
        trickPredictionMap.clear();
        trickCountMap.clear();
    }

    public void recordTrickPrediction(Player player, int trickPrediction) {
        if(trickPredictionMap.containsKey(player.getPlayerId())) {
            trickPredictionMap.put(player.getPlayerId(), trickPrediction);
        }
    }

    public void addToTrickCount(Player player) {
        if(trickCountMap.containsKey(player.getPlayerId())) {
            int currentTrickCount = trickCountMap.get(player.getPlayerId());
            trickCountMap.replace(player.getPlayerId(), currentTrickCount + 1);
        }
    }

    public void recordScores(LinkedList<Player> players) {
        for(Player player: players) {
            if(trickPredictionMap.containsKey(player.getPlayerId())
                    && trickPredictionMap.containsKey(player.getPlayerId())
                    && scoreMap.containsKey(player.getPlayerId())) {

                int trickResult = trickCountMap.get(player.getPlayerId());
                int trickPrediction = trickPredictionMap.get(player.getPlayerId());
                int oldScore = scoreMap.get(player.getPlayerId());
                int newScore;
                if (trickPrediction == trickResult) {
                    newScore = oldScore + 20 + (10 * trickResult);
                } else {
                    newScore = oldScore - (10 * Math.abs(trickPrediction - trickResult));
                }
                scoreMap.replace(player.getPlayerId(), newScore);
            }
        }
    }

    public List<Player> determineWinners(List<Player> players) {
        LinkedList<Player> winners = new LinkedList<>();

        if (scoreMap.isEmpty()) return winners;

        int maxScore = 0;
        for(Player player: players) {
            if(scoreMap.containsKey(player.getPlayerId())) {
                int score = scoreMap.get(player.getPlayerId());
                if(score > maxScore) {
                    winners.clear();
                    winners.add(player);
                    maxScore = score;
                } else if (score == maxScore) {
                    winners.add(player);
                }
            }
        }

        return winners;
    }

}
