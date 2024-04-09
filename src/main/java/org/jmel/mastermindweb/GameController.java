package org.jmel.mastermindweb;

import org.jmel.mastermind.core.Game;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class GameController {
    private static final Map<UUID, Game> games = new HashMap<>();

    @GetMapping("/new")
    public String createGame() {
        UUID gameId = UUID.randomUUID();
        try {
            games.put(gameId, new Game.Builder().build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "Your gameId: " + gameId;
    }

    @GetMapping("/get/{id}")
    public String findGame(@PathVariable UUID id) {
        Game game = games.get(id);
        if (game == null) {
            return "Game not found";
        }

        return game.isGameWon() ? "You already won!" : game.movesCompleted() < game.maxAttempts() ? "Game in progress" : "You lost!";
    }
}
