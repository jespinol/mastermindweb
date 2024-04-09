package org.jmel.mastermindweb.controller;

import org.jmel.mastermind.core.Game;
import org.jmel.mastermind.core.feedbackstrategy.Feedback;
import org.jmel.mastermindweb.model.GameState;
import org.jmel.mastermindweb.service.GameService;
import org.jmel.mastermindweb.model.GameSession;
import org.jmel.mastermindweb.model.MastermindConfig;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
public class GameController {
    public Map<UUID, GameSession> sessions = new HashMap<>();

    @PostMapping("/new")
    public UUID createGame(@RequestBody Optional<MastermindConfig> configInput) throws IOException {
        MastermindConfig config = configInput.orElseGet(MastermindConfig::new);
        GameSession gameSession = GameService.createGameSession(config);
        sessions.put(gameSession.getId(), gameSession);

        return gameSession.getId();
    }

    @GetMapping("/gameInfo")
    public GameState gameInfo(@RequestParam("id") UUID id) {
        if (!sessions.containsKey(id)) throw new IllegalArgumentException("Session not found");
        Game game = findGameById(id);

        return GameService.getGameState(game);
    }

    @PostMapping("/guess")
    public Feedback processGuess(@RequestParam("id") UUID id, @RequestBody List<Integer> guess) {
        if (!sessions.containsKey(id)) throw new IllegalArgumentException("Session not found");
        Game game = findGameById(id);

        return game.processGuess(guess);
    }

    private Game findGameById(UUID id) {
        return sessions.get(id).getGame();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    void handleIllegalArgumentException() {
        // Do nothing
    }

}
