package sample.threads;

import sample.models.Dice;
import sample.models.Game;

public class SecondDiceThread implements Runnable{
    Game game;
    private Dice[] oldRoll;

    public SecondDiceThread(Game game,Dice[] oldRoll) {
        this.game = game;
        this.oldRoll = oldRoll;
    }

    @Override
    public void run() {
        game.playerTwoRoll(oldRoll);
    }
}
