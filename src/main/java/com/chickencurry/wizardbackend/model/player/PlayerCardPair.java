package com.chickencurry.wizardbackend.model.player;

import com.chickencurry.wizardbackend.model.card.Card;
import com.chickencurry.wizardbackend.model.player.Player;

public record PlayerCardPair(Player player, Card card) {}
