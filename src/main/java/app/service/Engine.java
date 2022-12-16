package app.service;

import app.dao.GameDataBaseDao;
import app.entities.Game;
import app.entities.Guess;
import app.entities.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Arrays;
import java.util.List;

@Repository
public class Engine {
    private final GameDataBaseDao dao;


    @Autowired
    public Engine(GameDataBaseDao dao) {
        System.out.println("Constructor on");
        this.dao = dao;

    }



    public Integer[] generateRandomArrayInDao() {
        System.out.println("Generating random Array");
        Integer generatedArray[] = {0, 0, 0, 0};
        boolean isDuplicate = false;
        for (int i = 0; i < generatedArray.length; i++) {
            int y = (int) (Math.random() * 9) + 1;
            for (int j = 0; j < generatedArray.length; j++) {
                if (generatedArray[j] == y) {
                    isDuplicate = true; // Flag used to denote duplicate
                    break;
                }
            }

            if (isDuplicate) {
                i--;
                isDuplicate = false;
            } else {
                generatedArray[i] = y;
                // System.out.println(generatedArray[i]);
            }
        }
        // String generatedArrayString = Arrays.toString(generatedArray);
        return generatedArray;
    }


    public String epPositionsInEngine(int gameId, String userNumbers) {
        System.out.println("Generating EP...");
        String secretNumbers = findGameByIdForComparison(gameId).getSecretNumbers();
        System.out.println("secretNumbers");
        String EP = "NNNN";

        for (int i = 0; i < secretNumbers.length(); i++) {
            for (int j = 0; j < userNumbers.length(); j++) {
                if (i != j && secretNumbers.charAt(i) == userNumbers.charAt(j)) {
                    for (int letter = 0; letter < EP.length(); letter++) {
                        EP=replaceCharUsingCharArray(EP, 'P', letter);
                    }
                }

                if (secretNumbers.charAt(i) == userNumbers.charAt(i)) {
                    for (int letter = 0; letter < EP.length(); letter++) {
                        EP=replaceCharUsingCharArray(EP, 'E', letter);
                    }
                }
            }
        }
        System.out.println(EP);
        return EP;
    }

    public String replaceCharUsingCharArray(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }

    public List<Round> getRound()
    {
        return dao.findAllRounds();
    }

    public Game createGame() {
        System.out.println("Creating game...");
        Game game = new Game();
        game.setFinished(false);
        game.setSecretNumbers(Arrays.toString(this.generateRandomArrayInDao()));
        dao.createInGameTable(game);
        //game.setSecretNumbers("????");
        return game;
    }

    public Round createRound(Guess guess) {
        System.out.println("Creating round...");
        Game game = this.findGameByIdForComparison(guess.getGameId());
        if (!game.isFinished()) {
            Round round = new Round();
            round.setGameId(guess.getGameId());
            round.setEP(this.epPositionsInEngine(guess.getGameId(), guess.getUserNumbers()));
            if (round.getEP() == "EEEE") {
                this.isWinGame(game);
            }
            round.setEP(this.epPositionsInEngine(guess.getGameId(), guess.getUserNumbers()));
            round.setPlayerNumbers(guess.getUserNumbers());
            return dao.createInRoundTable(round);
        }
        return null;
    }


    public List<Game> getAllGamesInEngine() {
        List<Game> games = dao.getAllGames();
        return games;
    }


   public Game findGameByIdInEngine(int id) {
        Game game = dao.findByIdGamesInDao(id);
        /*if (!game.isFinished()) {
            game.setSecretNumbers("????");
        }*/
        return game;
    }

    public Game findGameByIdForComparison(int id) {
        Game game = dao.findByIdGamesInDao(id);
        /*if (!game.isFinished()) {
            game.setSecretNumbers("????");
        }*/
        return game;
    }



    public List<Round> getRoundsByGameIdInEngine(int id) {
        return dao.findByIdRoundsInDao(id);
    }




    public boolean isWinGame(Game game) {
        game.setFinished(true);
        return dao.updateGameFinishStatus(game);
    }

    /*public boolean areArraysEqual(Integer []guess){
        System.out.println("Are Arrays equal boolean check ");
        if (Arrays.equals( this.generateRandomArray(), guess)){
            return true;
        }
        return false;
    }*/


}
