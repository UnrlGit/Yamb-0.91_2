package sample.unused;

import sample.models.Dice;
import sample.models.Game;

public class FirstDiceThread implements Runnable {
    Game game;
    private Dice[] oldRoll;

    public FirstDiceThread(Game game,Dice[] oldRoll) {
        this.game = game;
        this.oldRoll = oldRoll;
    }

    @Override
    public void run() {
        game.playerOneRoll(oldRoll);

    }
}
