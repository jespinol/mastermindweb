package org.jmel.mastermindweb.controller;

import org.jmel.mastermind.core.Game;
import org.jmel.mastermindweb.dto.GameState;
import org.jmel.mastermindweb.service.GameService;
import org.jmel.mastermindweb.dto.MastermindConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
public class GameController {
    public Map<UUID, Game> sessions = new HashMap<>();

    @PostMapping("/new")
    public UUID createGame(@RequestBody Optional<MastermindConfig> configInput) throws IOException {
        MastermindConfig config = configInput.orElseGet(MastermindConfig::new);
        UUID id = UUID.randomUUID();
        Game game = GameService.createGame(config);
        sessions.put(id, game);

        return id;
    }

    @GetMapping("/gameInfo")
    public GameState gameInfo(@RequestParam("id") UUID id) {
        if (!sessions.containsKey(id)) throw new IllegalArgumentException("Session not found");
        Game game = findGameById(id);

        return GameService.getGameState(game);
    }

    @PostMapping("/guess")
    public String processGuess(@RequestParam("id") UUID id, @RequestBody List<Integer> guess) {
        if (!sessions.containsKey(id)) throw new IllegalArgumentException("Session not found");
        Game game = findGameById(id);

        return game.processGuess(guess).toString();
    }

    private Game findGameById(UUID id) {
        return sessions.get(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleHttpNotReadableException (HttpMessageNotReadableException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handleIOException(IOException e) {
        return e.getMessage();
    }
}
