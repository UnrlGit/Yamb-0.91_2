package sample.Server;

import sample.models.Dice;
import sample.models.DiceSet;

import java.rmi.RemoteException;

public class NetworkHandler implements DiceRollService {
//    @Override
//    public int rollDice() throws RemoteException {
//        return 7;
//    }


    @Override
    public DiceSet rollDice(DiceSet diceSet) throws RemoteException {
//        DiceSet ds = new DiceSet();
//        Dice one = new Dice(1, true);
//        Dice two = new Dice(2, true);
//        Dice three = new Dice(3, true);
//        Dice four = new Dice(4, true);
//        Dice five = new Dice(5, true);
//        Dice six = new Dice(1, true);
//
//        Dice[] dices = {one, two, three, four, five};
//
//        ds.setDices(dices);
//        return ds;
        DiceSet set = diceSet;
        for (Dice dice : diceSet.getDices()) {
            if (dice.isRoll()) {
                int rollValue = (int) (Math.random() * 6) + 1;
                dice.setValue(rollValue);
            }
        }
        return diceSet;
    }
}