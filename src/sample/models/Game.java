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
    private boolean playerTwoCanRoll = true;
    public boolean isPlayerOneCanRoll() {
        return playerOneCanRoll;
    }
    public void setPlayerOneCanRoll(boolean playerOneCanRoll) {
        this.playerOneCanRoll = playerOneCanRoll;
    }
    public boolean isPlayerTwoCanRoll() {
        return playerTwoCanRoll;
    }
    public void setPlayerTwoCanRoll(boolean playerTwoCanRoll) {
        this.playerTwoCanRoll = playerTwoCanRoll;
    }



    private static final int NUMBER_OF_TURNS = 13;

    private Player player;
//    private Player playerTwo;
    private DiceSet diceSet;
    private RollCount playerOneRollCount = RollCount.ZERO;
    private RollCount playerTwoRollCount = RollCount.ZERO;
    private boolean playerOneRolling;
    private boolean playerTwoRolling;
    private boolean diceSpinning;

    private int[] playerOneLastRoll;
    private int[] playerTwoLastRoll;

    public Game(Player player, GameType gameType) {
        this.player = player;
        //this.playerTwo = playerTwo;
        this.diceSet = new DiceSet();
        this.playerOneRolling = false;
        this.playerTwoRolling = false;
        this.diceSpinning = false;
        this.playerOneLastRoll = new int[]{1, 1, 1, 1, 1};
        this.playerTwoLastRoll = new int[]{1, 1, 1, 1, 1};

        this.gameType = gameType;
    }

    // SETTERS & GETTERS

    public int[] getPlayerOneLastRoll() {
        return playerOneLastRoll;
    }

    public void setPlayerOneLastRoll(int[] playerOneLastRoll) {
        this.playerOneLastRoll = playerOneLastRoll;
    }

    public int[] getPlayerTwoLastRoll() {
        return playerTwoLastRoll;
    }

    public void setPlayerTwoLastRoll(int[] playerTwoLastRoll) {
        this.playerTwoLastRoll = playerTwoLastRoll;
    }

    public RollCount getPlayerOneRollCount() {
        return playerOneRollCount;
    }

    public void setPlayerOneRollCount(RollCount playerOneRollCount) {
        this.playerOneRollCount = playerOneRollCount;
    }

    public RollCount getPlayerTwoRollCount() {
        return playerTwoRollCount;
    }

    public void setPlayerTwoRollCount(RollCount playerTwoRollCount) {
        this.playerTwoRollCount = playerTwoRollCount;
    }

    public boolean isPlayerOneRolling() {
        return playerOneRolling;
    }

    public void setPlayerOneRolling(boolean playerOneRolling) {
        this.playerOneRolling = playerOneRolling;
    }

    public boolean isPlayerTwoRolling() {
        return playerTwoRolling;
    }

    public void setPlayerTwoRolling(boolean playerTwoRolling) {
        this.playerTwoRolling = playerTwoRolling;
    }

    public boolean isDiceSpinning() {
        return diceSpinning;
    }

    public void setDiceSpinning(boolean diceSpinning) {
        this.diceSpinning = diceSpinning;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

//    public Player getPlayerTwo() {
//        return playerTwo;
//    }


//    public void setPlayerTwo(Player playerTwo) {
//        this.playerTwo = playerTwo;
//    }

    public DiceSet getDiceSet() {
        return diceSet;
    }

    public void setDiceSet(DiceSet diceSet) {
        this.diceSet = diceSet;
    }


    // METHODS
    public void playerOneRoll(Dice[] oldRoll) {
        synchronized (this) {
            if(playerOneRollCount == RollCount.THIRD) {
                return;
            }
            if (playerTwoRolling) {
                        try {

                    setPlayerOneCanRoll(false);

                    System.out.println("Player two rolling");
                    wait();
                    System.out.println("Player two done rolling");
                    setPlayerOneCanRoll(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            playerOneRolling = true;
            this.getDiceSet().setDices(oldRoll);
            if (playerOneRollCount == RollCount.ZERO) {
                for (int i = 0; i < 5; i++) {
                    int rollValue = (int) (Math.random() * 6) + 1;
                    playerOneLastRoll[i] = rollValue;
                    diceSet.getDices()[i].setValue(rollValue);
                }
                player.getYambPaper().setWrittenSomething(false);
                playerOneRollCount = RollCount.FIRST;
                return;
            }
            if (playerOneRollCount == RollCount.FIRST) {
                for (int i = 0; i < 5; i++) {
                    if (diceSet.getDices()[i].isRoll()) {
                        int rollValue = (int) (Math.random() * 6) + 1;
                        playerOneLastRoll[i] = rollValue;
                        diceSet.getDices()[i].setValue(rollValue);
                    }
                    playerOneRollCount=RollCount.SECOND;
                    return;
                }
            }
            if (playerOneRollCount == RollCount.SECOND) {
                for (int i = 0; i < 5; i++) {
                    if (diceSet.getDices()[i].isRoll()) {
                        int rollValue = (int) (Math.random() * 6) + 1;
                        playerOneLastRoll[i] = rollValue;
                        diceSet.getDices()[i].setValue(rollValue);
                    }
                }
                playerOneRollCount = RollCount.THIRD;
                playerOneRolling = false;
                notifyAll();
            }
            if(playerOneRollCount == RollCount.RESET)
            {
                playerOneRollCount = RollCount.ZERO;
                getPlayer().getYambPaper().setWrittenSomething(true);
                playerOneRolling = false;
                notifyAll();
            }

        }
    }

//    public void playerTwoRoll(Dice[] oldRoll) {
//        if(playerTwoRollCount == RollCount.THIRD) {
//            return;
//        }
//        synchronized (this) {
//            if (playerOneRolling) {
//                try {
//                    System.out.println(playerOneLastRoll[0] + " " +playerOneLastRoll[1] + " " +playerOneLastRoll[2]);
//                    setPlayerTwoCanRoll(false);
//                    System.out.println("Player one rolling");
//                    wait();
//                    System.out.println("Player one done rolling");
//                    setPlayerTwoCanRoll(true);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            playerTwoRolling = true;
//            this.getDiceSet().setDices(oldRoll);
//            if (playerTwoRollCount == RollCount.ZERO) {
//                for (int i = 0; i < 5; i++) {
//                    int rollValue = (int) (Math.random() * 6) + 1;
//                    diceSet.getDices()[i].setValue(rollValue);
//                    playerTwoLastRoll[i] = rollValue;
//
//                }
//                playerTwo.getYambPaper().setWrittenSomething(false);
//                playerTwoRollCount = RollCount.FIRST;
//                return;
//            }
//            if (playerTwoRollCount == RollCount.FIRST) {
//                for (int i = 0; i < 5; i++) {
//                    if (diceSet.getDices()[i].isRoll()) {
//                        int rollValue = (int) (Math.random() * 6) + 1;
//                        diceSet.getDices()[i].setValue(rollValue);
//                        playerTwoLastRoll[i] = rollValue;
//                    }
//                }
//                playerTwoRollCount=RollCount.SECOND;
//                return;
//            }
//            if (playerTwoRollCount == RollCount.SECOND) {
//                for (int i = 0; i < 5; i++) {
//                    if (diceSet.getDices()[i].isRoll()) {
//                        int rollValue = (int) (Math.random() * 6) + 1;
//                        diceSet.getDices()[i].setValue(rollValue);
//                        playerTwoLastRoll[i] = rollValue;
//                    }
//                }
//                playerTwoRollCount = RollCount.THIRD;
//            }
//            if(playerTwoRollCount == RollCount.RESET)
//            {
//                playerTwoRollCount = RollCount.ZERO;
//                getPlayerTwo().getYambPaper().setWrittenSomething(true);
//            }
//            playerTwoRolling = false;
//            notifyAll();
//        }
//    }


}
