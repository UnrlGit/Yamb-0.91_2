package sample.threads;

import sample.Server.SimpleServerPlayerData;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClearClientsServerThread implements Runnable{
    final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
    private static ArrayList<SimpleServerPlayerData> playersArray;

    public ClearClientsServerThread(ArrayList<SimpleServerPlayerData> playersArray) {
        this.playersArray = playersArray;
    }

    @Override
    public void run() {

        ses.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                playersArray.clear();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }
}
