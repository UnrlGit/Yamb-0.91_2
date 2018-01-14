package sample.models;

import sample.enums.GameType;
import sample.enums.RollCount;

import java.io.Serializable;

public class Game implements Serializable {
    //SINGLE VS MULTI PLAYER
    private GameType gameType;

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    //ADDED FOR DISABLING ROLL BUTTON
    private boolean playerOneCanRoll = true;
    public boolean isPlayerOneCanRoll() {
        return playerOneCanRoll;
    }

    private Player player;
    private DiceSet diceSet;
    private RollCount playerOneRollCount = RollCount.ZERO;

    public Game(Player player, GameType gameType) {
        this.player = player;
        this.diceSet = new DiceSet();
        this.gameType = gameType;
    }

    // SETTERS & GETTERS
    public RollCount getPlayerOneRollCount() {
        return playerOneRollCount;
    }

    public void setPlayerOneRollCount(RollCount playerOneRollCount) {
        this.playerOneRollCount = playerOneRollCount;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public DiceSet getDiceSet() {
        return diceSet;
    }

    public void setDiceSet(DiceSet diceSet) {
        this.diceSet = diceSet;
    }

}
