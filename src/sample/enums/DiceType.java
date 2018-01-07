package sample.enums;

import java.io.Serializable;

public enum DiceType implements Serializable {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);

    private int diceType;

    DiceType(int diceType) {
        this.diceType = diceType;
    }
}
