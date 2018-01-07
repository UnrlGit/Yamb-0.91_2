package sample.Server;

import sample.models.Dice;
import sample.models.DiceSet;
import java.rmi.RemoteException;

public class NetworkHandler implements DiceRollService {
    boolean alreadyRolling = false;

    @Override
    public  DiceSet rollDice(DiceSet diceSet) throws RemoteException {
        synchronized (this) {
            if (alreadyRolling) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            alreadyRolling = true;
            DiceSet set = diceSet;
            for (Dice dice : diceSet.getDices()) {
                if (dice.isRoll()) {
                    int rollValue = (int) (Math.random() * 6) + 1;
                    dice.setValue(rollValue);
                }
            }
            alreadyRolling = false;
            notifyAll();
            return diceSet;
        }
    }
}