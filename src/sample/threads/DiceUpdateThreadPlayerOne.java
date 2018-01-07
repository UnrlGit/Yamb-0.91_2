package sample.threads;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.enums.RollCount;
import sample.models.Game;

import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class DiceUpdateThreadPlayerOne implements Runnable {
    Game game;
    ToggleButton diceOne;
    ToggleButton diceTwo;
    ToggleButton diceThree;
    ToggleButton diceFour;
    ToggleButton diceFive;
    Label rollCount;
    Button btnRollDice;

    public DiceUpdateThreadPlayerOne(Game game, ToggleButton diceOne, ToggleButton diceTwo,
                                     ToggleButton diceThree, ToggleButton diceFour,
                                     ToggleButton diceFive, Label rollCount, Button btnRollDice) {
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
                // use Platform.runLater(Runnable runnable) If you need to update a GUI component from a non-GUI thread.
                Platform.runLater(new Runnable() {
                    public void run() {
//                        if (game.getPlayerOneRollCount() == 1 || game.getPlayerOneRollCount() == 2 || game.getPlayerOneRollCount() == 3) {
                            diceSetUpdate();
//                        }
                    }
                });
            }
        }, 10, 10);

    }

    private void diceSetUpdate() {
//        diceOne.setText(String.valueOf(game.getPlayerOneLastRoll()[0]));
//        diceTwo.setText(String.valueOf(game.getPlayerOneLastRoll()[1]));
//        diceThree.setText(String.valueOf(game.getPlayerOneLastRoll()[2]));
//        diceFour.setText(String.valueOf(game.getPlayerOneLastRoll()[3]));
//        diceFive.setText(String.valueOf(game.getPlayerOneLastRoll()[4]));

        diceOne.setText(String.valueOf(game.getDiceSet().getDices()[0].getValue()));
        diceTwo.setText(String.valueOf(game.getDiceSet().getDices()[1].getValue()));
        diceThree.setText(String.valueOf(game.getDiceSet().getDices()[2].getValue()));
        diceFour.setText(String.valueOf(game.getDiceSet().getDices()[3].getValue()));
        diceFive.setText(String.valueOf(game.getDiceSet().getDices()[4].getValue()));
        setPictures();

        rollCount.setText("ROLL: " + String.valueOf(game.getPlayerOneRollCount()));

        if (game.isPlayerOneCanRoll() == false){
            btnRollDice.setDisable(true);
            btnRollDice.setText("Waiting");
        }else{
            btnRollDice.setDisable(false);
            btnRollDice.setText("Roll Dice");
        }
    }

    private void setPictures() {
        diceOne.setGraphic(numberToPng(diceOne.getText()));
        diceTwo.setGraphic(numberToPng(diceTwo.getText()));
        diceThree.setGraphic(numberToPng(diceThree.getText()));
        diceFour.setGraphic(numberToPng(diceFour.getText()));
        diceFive.setGraphic(numberToPng(diceFive.getText()));
    }

    private ImageView numberToPng(String text) {
        Image img = new Image("sample/png/Alea_1.png");
        switch (text){
            case "1":
                break;
            case "2":
                img = new Image("sample/png/Alea_2.png");
                break;
            case "3":
                img = new Image("sample/png/Alea_3.png");
                break;
            case "4":
                img = new Image("sample/png/Alea_4.png");
                break;
            case "5":
                img = new Image("sample/png/Alea_5.png");
                break;
            case "6":
                img = new Image("sample/png/Alea_6.png");
                break;
                default:
                    break;
        }

        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(20.0);
        imageView.setFitWidth(20.0);

        return imageView;
    }
}

