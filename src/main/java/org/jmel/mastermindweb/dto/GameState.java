package org.jmel.mastermindweb.dto;

public record GameState(int codeLength, int numColors, int maxAttempts, boolean gameWon, int movesCompleted) {
}
