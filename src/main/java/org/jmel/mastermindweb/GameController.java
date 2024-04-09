package org.jmel.mastermindweb;

import org.jmel.mastermind.core.Game;
import org.jmel.mastermind.core.feedbackstrategy.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
public class GameController {
    public Map<UUID, GameSession> sessions = new HashMap<>();

    @PostMapping("/new")
    public UUID createGame(@RequestBody Optional<MastermindConfig> configInput) throws IOException {
        UUID gameId = UUID.randomUUID();
        MastermindConfig configuration = configInput.orElseGet(MastermindConfig::new);

        GameSession gameSession = new GameSession();
        gameSession.setId(gameId);
        gameSession.setConfiguration(configuration);
        gameSession.setGame(new Game.Builder().build()); // TODO: use configurations for game builder
        sessions.put(gameId, gameSession);

        return gameId;
    }

    @GetMapping("/gameInfo")
    public String findSession(@RequestParam("id") UUID id) {
        if (!sessions.containsKey(id)) return "not found";

        Game game = findGameById(id);
        if (game.isGameWon()) return "You already won!";
        if (game.movesCompleted() == game.maxAttempts()) return "Game over!";

        return "Game in progress. ";
    }

    @PostMapping("/guess")
    public Feedback processGuess(@RequestParam("id") UUID id, @RequestBody List<Integer> guess) {
        Game game = findGameById(id);

        return game.processGuess(guess);
    }

    private Game findGameById(UUID id) {
        return sessions.get(id).getGame();
    }
}
