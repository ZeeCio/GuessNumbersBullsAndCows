package app.entities;

public class Round {

        private int id;
        private int gameId;
        private String playerNumbers;

        private String EP;

    public Round() {
    }

    public Round(String playerNumbers) {
        this.playerNumbers = playerNumbers;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getPlayerNumbers() {
        return playerNumbers;
    }

    public void setPlayerNumbers(String playerNumbers) {
        this.playerNumbers = playerNumbers;
    }

    public String getEP() {
        return EP;
    }

    public void setEP(String EP) {
        this.EP = EP;
    }


}
