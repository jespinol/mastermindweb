package org.jmel.mastermindweb.controller;

import org.jmel.mastermind.core.Game;
import org.jmel.mastermind.core.feedbackstrategy.Feedback;
import org.jmel.mastermindweb.dto.GameState;
import org.jmel.mastermindweb.dto.MastermindConfig;
import org.jmel.mastermindweb.dto.Session;
import org.jmel.mastermindweb.dto.SessionRepository;
import org.jmel.mastermindweb.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class GameController {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private GameService GameService;

    @PostMapping("/new")
    public UUID createGame(@RequestBody Optional<MastermindConfig> configInput) throws IOException {
        MastermindConfig config = configInput.orElseGet(MastermindConfig::new);
        Game game = GameService.createGame(config);

        Session session = new Session();
        session.setGames(game);
        sessionRepository.save(session);

        return session.getId();
    }

    @GetMapping("/gameInfo")
    public GameState gameInfo(@RequestParam("id") UUID id) {
        Optional<Session> session_opt = sessionRepository.findById(id);
        if (session_opt.isEmpty()) throw new IllegalArgumentException("Game not found");

        Game game = session_opt.get().getGame();

        return GameService.getGameState(game);
    }

    @PostMapping("/guess")
    public String processGuess(@RequestParam("id") UUID id, @RequestBody List<Integer> guess) {
        Optional<Session> session_opt = sessionRepository.findById(id);
        if (session_opt.isEmpty()) throw new IllegalArgumentException("Session not found");

        Session session = session_opt.get();
        Game game = session.getGame();
        Feedback feedback = game.processGuess(guess);
        sessionRepository.save(session);

        return feedback.toString();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleHttpNotReadableException(HttpMessageNotReadableException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handleIOException(IOException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handleIllegalStateException(IllegalStateException e) {
        return e.getMessage();
    }
}
