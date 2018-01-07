package sample.models;

import java.io.Serializable;
import java.util.UUID;

public class Player implements Serializable {
    private YambPaper yambPaper;
    private String nick;
    private UUID id;
    private boolean rollsFirst;
    private boolean gameComplete;

    public boolean isGameComplete() {
        return gameComplete;
    }

    public void setGameComplete(boolean gameComplete) {
        this.gameComplete = gameComplete;
    }

    public Player(String nick, boolean rollsFirst) {
        this.yambPaper = new YambPaper();
        this.nick = nick;
        this.rollsFirst = rollsFirst;
        this.id = UUID.randomUUID();
        this.gameComplete = false;
    }

    public boolean isRollsFirst() {
        return rollsFirst;
    }

    public void setRollsFirst(boolean rollsFirst) {
        this.rollsFirst = rollsFirst;
    }

    public YambPaper getYambPaper() {
        return yambPaper;
    }

    public YambPaper getYambPaperCopy() {
        return new YambPaper(yambPaper.getDown(), yambPaper.getUp(), yambPaper.getFree(), yambPaper.getCall());
    }

    public void setYambPaper(YambPaper yambPaper) {
        this.yambPaper = yambPaper;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
