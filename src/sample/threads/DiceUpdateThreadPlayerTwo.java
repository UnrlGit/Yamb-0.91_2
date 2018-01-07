package sample.threads;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import sample.models.Game;

import java.util.Timer;
import java.util.TimerTask;

public class DiceUpdateThreadPlayerTwo implements Runnable{
    Game game;
    ToggleButton diceOne;
    ToggleButton diceTwo;
    ToggleButton diceThree;
    ToggleButton diceFour;
    ToggleButton diceFive;
    Label rollCount;
    Button btnRollDice;

    public DiceUpdateThreadPlayerTwo(Game game, ToggleButton diceOne, ToggleButton diceTwo, ToggleButton diceThree, ToggleButton diceFour, ToggleButton diceFive, Label rollCount, Button btnRollDice) {
        this.game = game;
        this.diceOne = diceOne;
        this.diceTwo = diceTwo;
        this.diceThree = diceThree;
        this.diceFour = diceFour;
        this.diceFive = diceFive;
        this.rollCount = rollCount;
        this.btnRollDice = btnRollDice;
    }


    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() { // timer task to update the seconds
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                            diceSet();
                    }
                });
            }
        }, 10, 10);
    }

    private void diceSet() {
        diceOne.setText(String.valueOf(game.getPlayerTwoLastRoll()[0]));
        diceTwo.setText(String.valueOf(game.getPlayerTwoLastRoll()[1]));
        diceThree.setText(String.valueOf(game.getPlayerTwoLastRoll()[2]));
        diceFour.setText(String.valueOf(game.getPlayerTwoLastRoll()[3]));
        diceFive.setText(String.valueOf(game.getPlayerTwoLastRoll()[4]));

        rollCount.setText("ROLL: " + String.valueOf(game.getPlayerTwoRollCount()));

        if (game.isPlayerTwoCanRoll() == false){
            btnRollDice.setDisable(true);
            btnRollDice.setText("Waiting");
        }else{
            btnRollDice.setDisable(false);
            btnRollDice.setText("Roll Dice");
        }
    }

}
