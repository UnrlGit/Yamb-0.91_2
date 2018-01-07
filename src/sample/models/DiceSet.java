package sample.models;

import java.io.Serializable;
import java.security.SecureRandom;

public class DiceSet implements Serializable {
    private static final int MIN = 1;
    private static final int MAX = 6;
    private Dice[] dices;
    private int numberOfDice;
    private boolean isRolling;

    public DiceSet() {
        this.dices = new Dice[5];
        this.numberOfDice = 5;
        this.isRolling = false;

    }

    public Dice[] getDices() {
        return dices;
    }

    public void setDices(Dice[] dices) {
        this.dices = dices;
    }

    public int getDiceTotalValue(){
        int total = 0;
        for (Dice dice:dices) {
            total+= dice.getValue();
        }
        return total;
    }

    public void setAllDiceToRoll(){
        for (Dice dice:dices) {
            dice.setRoll(true);
        }
    }






}
