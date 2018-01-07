package sample.enums;

import java.io.Serializable;

public enum RollCount implements Serializable{
    ZERO(0), FIRST(1), SECOND(2), THIRD(3), RESET(-1);

    private int rollStatus;

    RollCount(int status) {
        this.rollStatus = status;
    }

    public int getRollStatus() {
        return rollStatus;
    }
}
