package org.jmel.mastermindweb.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public record GameStateRecord(int codeLength, int numColors, int maxAttempts, boolean gameWon, int movesCompleted) {
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not create JSON object", e);
        }
    }
}
