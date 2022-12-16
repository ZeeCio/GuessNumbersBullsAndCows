package app.controller;

import app.dao.GameDataBaseDao;
import app.entities.Game;
import app.entities.Guess;
import app.entities.Round;
import app.service.Engine;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
public class GameController {

    private final Engine engine;
    private final GameDataBaseDao dao;
    private final Game game;


    public GameController(Engine engine, GameDataBaseDao dao, Game game) {
        this.engine = engine;
        this.dao = dao;
        this.game = game;
    }

    // Working
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(Game game) {
        System.out.println("Creating game...");
        return engine.createGame();
    }

    // Working
    @PutMapping("/takeGuess")
    public Round makeGuess(int i, int j, int k, int l) {
        System.out.println("Taking guess input");
        Integer[] guessNumbers = {i, j, k, l};

        System.out.println("Inputted 4 figures");
        String guessS = Arrays.toString(guessNumbers);
        Guess guess = new Guess(guessS);
        //Guess guess1 = new Guess(guess);
        Round round = new Round(guessS);
        if (guess.equals(game.getSecretNumbers())) {
            engine.createRound(guess);
        }
        return round;
    }


    @PostMapping("/guess")
    public ResponseEntity<Round> guessNumbers(@RequestBody Guess guess) {
        System.out.println("Guess post-mapping");
        Round round = engine.createRound(guess);
        if (round == null) {
            return new ResponseEntity(null, HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(round);
    }


    // Working
    @GetMapping("/listGames")
    public List<Game> all() {
        List<Game> all = engine.getAllGamesInEngine();
        for (Game each : all) {
            System.out.println(each.toString());
        }
        return all;
    }

    // Working
    @GetMapping("/rounds")
    public List<Round> allRounds() {
        List<Round> allRounds = engine.getRound();
        for (Round each : allRounds) {
            System.out.println(each.toString());
        }
        return allRounds;
    }

    // Working
    @GetMapping("/games/{gameId}")
    public ResponseEntity<Game> findGameByIdController(@RequestParam Integer id) {
        Game result = engine.findGameByIdForComparison(id);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    // Working - try with gameid=1
    @GetMapping("/rounds/{gameId}")
    public ResponseEntity<List<Round>> findRoundByIdController(@RequestParam Integer id) {
        List<Round> result = engine.getRoundsByGameIdInEngine(id);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

}
