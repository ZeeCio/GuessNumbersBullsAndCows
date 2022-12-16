package app.entities;

import org.springframework.beans.factory.annotation.Autowired;

public class Guess {
    private int gameId;
    private String userNumbers;

    @Autowired
    public Guess(String userNumbers) {
        this.userNumbers = userNumbers;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getUserNumbers() {
        return userNumbers;
    }

    public void setUserNumbers(String userNumbers) {
        this.userNumbers = userNumbers;
    }

}
