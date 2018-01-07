package sample.models;

import java.io.Serializable;

public class Dice implements Serializable {
    private int value;
    private boolean roll;
    private String diceMark;

    public String getDiceMark() {
        return diceMark;
    }

    public void setDiceMark(String diceMark) {
        this.diceMark = diceMark;
    }

    public Dice(int value, boolean roll, String diceMark) {

        this.value = value;
        this.roll = roll;
        this.diceMark = diceMark;
    }

    public Dice(int value, boolean roll) {
        this.value = value;
        this.roll = roll;
    }

    public Dice() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRoll() {
        return roll;
    }

    public void setRoll(boolean roll) {
        this.roll = roll;
    }


}
