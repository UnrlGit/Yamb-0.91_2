package sample.threads;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import sample.Server.SimpleServerPlayerData;
import sample.models.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ClientServerScoreUpdate implements Runnable {
    private TextArea playersTextArea;
    Player player;

    public ClientServerScoreUpdate(TextArea playersTextArea, Player player) {
        this.playersTextArea = playersTextArea;
        this.player = player;
    }

    @Override
    public synchronized void run() {
        Timer timer = new Timer(true);

        timer.schedule(new TimerTask() { // timer task to update the seconds

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public synchronized void run() {
                        StringBuilder sb = new StringBuilder();
                        ArrayList<SimpleServerPlayerData> playersData = updateScore();
                        setWinConditions(playersData);
                        for (SimpleServerPlayerData player:playersData) {
                            sb.append(player.getNick()+" "+player.getScore());
                            sb.append(System.getProperty("line.separator"));
                        }
                        playersTextArea.setText(sb.toString());

                    }
                });
            }
        }, 500, 500);
    }

    private void setWinConditions(ArrayList<SimpleServerPlayerData> playerData){
        boolean allPlayersDone = true;
        for(SimpleServerPlayerData s:playerData){
            if(s.isGameComplete() == false){allPlayersDone = false;}
        }

        if (allPlayersDone && playerData.size() > 0){
            SimpleServerPlayerData winner = playerData.get(0);
            for(SimpleServerPlayerData s:playerData){
                if(s.getScore() >= winner.getScore()){
                    winner = s;
                }
            }
            String name = winner.getNick();
            winner.setNick("WINNER: " + name);
        }

            for(SimpleServerPlayerData s:playerData){
                boolean notWinner = true;
                if (s.getNick().length() > 7){
                    if (s.getNick().substring(0, 8).equals("WINNER: ")){
                      notWinner = false;
                     }
                }
                if(s.isGameComplete() && notWinner){
                    s.setNick("GAME COMPLETE: "+ s.getNick());
                }
            }

    }

    private  ArrayList<SimpleServerPlayerData> updateScore() {
        try (Socket socket = new Socket("localhost", 4321)) { //or localhost instead of 127...
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                SimpleServerPlayerData sspd = new SimpleServerPlayerData(
                        player.getYambPaper().getTotal(), player.getNick(), player.getId(), player.isGameComplete());
                outputStream.writeObject(sspd);


                ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
                ArrayList<SimpleServerPlayerData> ssdp = (ArrayList<SimpleServerPlayerData>) inStream.readObject();
                return  ssdp;


            } catch (SocketTimeoutException e) {
                System.out.println("The socked timed out");
            } catch (IOException e) {
                System.out.println("Client error" + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                e.printStackTrace();

            }
        } catch (UnknownHostException e) {
            System.out.println("crashed ukwnown" +
                    "");
            e.printStackTrace();

        } catch (IOException e) {

//            System.out.println("crashed IO"); e.printStackTrace();
            System.out.println("GameServer not started!");
            System.out.println("Please start it from Server package!");
            System.exit(0);

        }
        return null;
    }

    }


