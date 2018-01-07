package sample.Server;

import java.io.Serializable;
import java.util.UUID;

public class SimpleServerPlayerData implements Serializable {
    private int score;
    private String nick;
    private UUID id;
    private boolean gameComplete;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public SimpleServerPlayerData(int score, String nick, UUID id, boolean gameComplete) {
        this.score = score;
        this.nick = nick;
        this.id = id;
        this.gameComplete = gameComplete;

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public boolean isGameComplete() {
        return gameComplete;
    }

    public void setGameComplete(boolean gameComplete) {
        this.gameComplete = gameComplete;
    }


}
