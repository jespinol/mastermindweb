package org.jmel.mastermindweb.model;

public record GameState(int codeLength, int numColors, int maxAttempts, boolean gameWon, int movesCompleted) {
}
