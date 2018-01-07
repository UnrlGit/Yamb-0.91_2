package sample.Server;

import sample.models.DiceSet;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DiceRollService extends Remote {
//    int rollDice() throws RemoteException;
    DiceSet rollDice(DiceSet diceSet) throws RemoteException;
}
