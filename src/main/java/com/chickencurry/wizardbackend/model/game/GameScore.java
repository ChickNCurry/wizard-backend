package com.chickencurry.wizardbackend.model.game;

import com.chickencurry.wizardbackend.model.player.Player;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Component
public class GameScore {

    private final HashMap<String, Integer> trickPredictionMap;
    private final HashMap<String, Integer> trickCountMap;
    private final HashMap<String, Integer> scoreMap;

    public GameScore() {
        trickPredictionMap = new HashMap<>();
        trickCountMap = new HashMap<>();
        scoreMap = new HashMap<>();
    }

    public void addPlayer(Player player) {
        trickPredictionMap.put(player.getId(), 0);
        trickCountMap.put(player.getId(), 0);
        scoreMap.put(player.getId(), 0);
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
        if(trickPredictionMap.containsKey(player.getId())) {
            trickPredictionMap.put(player.getId(), trickPrediction);
        }
    }

    public void addToTrickCount(Player player) {
        if(trickCountMap.containsKey(player.getId())) {
            int currentTrickCount = trickCountMap.get(player.getId());
            trickCountMap.replace(player.getId(), currentTrickCount + 1);
        }
    }

    public void recordScores(LinkedList<Player> players) {
        for(Player player: players) {
            if(trickPredictionMap.containsKey(player.getId())
                    && trickPredictionMap.containsKey(player.getId())
                    && scoreMap.containsKey(player.getId())) {

                int trickResult = trickCountMap.get(player.getId());
                int trickPrediction = trickPredictionMap.get(player.getId());
                int oldScore = scoreMap.get(player.getId());
                int newScore;
                if (trickPrediction == trickResult) {
                    newScore = oldScore + 20 + (10 * trickResult);
                } else {
                    newScore = oldScore - (10 * Math.abs(trickPrediction - trickResult));
                }
                scoreMap.replace(player.getId(), newScore);
            }
        }
    }

    public List<Player> determineWinners(List<Player> players) {
        LinkedList<Player> winners = new LinkedList<>();

        if (scoreMap.isEmpty()) return winners;

        int maxScore = 0;
        for(Player player: players) {
            if(scoreMap.containsKey(player.getId())) {
                int score = scoreMap.get(player.getId());
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
