package app.entities;

import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
@Repository
public class Game {
    private int gameId;
    private String secretNumbers;
    private Timestamp game_time;

    private boolean isFinished;


    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getSecretNumbers() {
        return secretNumbers;
    }

    public void setSecretNumbers(String secretNumbers) {
        this.secretNumbers = secretNumbers;
    }

    public Timestamp getGame_time() {
        return game_time;
    }

    public void setGame_time(Timestamp game_time) {
        this.game_time = game_time;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }


}
