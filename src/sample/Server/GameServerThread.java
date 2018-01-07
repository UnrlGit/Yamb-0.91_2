package sample.Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class GameServerThread extends Thread{
//    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream inStream = null;
    private ArrayList<SimpleServerPlayerData> arrayList;
    private boolean allPlayersFinished;

    public GameServerThread(Socket socket, ArrayList<SimpleServerPlayerData> playerData){
        this.socket = socket;
        this.arrayList = playerData;
        this.allPlayersFinished = false;
    }

    @Override
    public synchronized void run() {
        try {
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            SimpleServerPlayerData ssdp = (SimpleServerPlayerData) inStream.readObject();


            boolean exists = false;
            for (SimpleServerPlayerData s:arrayList) {
                if(s.getId().equals(ssdp.getId())){
                    exists = true;
                    s.setScore(ssdp.getScore());
                    s.setGameComplete(ssdp.isGameComplete());
                }
            }
            if(!exists) {
                arrayList.add(ssdp);
            }
//
//
//            //GAME ENDING LOGIC
//            gameFinished();
//            if (allPlayersFinished == false) {
//                gameEndings();
//            }

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(arrayList);
        }catch(IOException e){
            System.out.println("Oops: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try{
                socket.close();
            } catch (IOException e) {
                System.out.println("Socket closing problems");
            }
        }
    }

    private boolean gameFinished() {
        boolean allPlayersComplete = true;
        String winLogo = "WINNER: ";

        for (SimpleServerPlayerData spd:arrayList) {
            if (spd.isGameComplete() == false){
                allPlayersComplete = false;
            }
        }

        if (allPlayersComplete)
        {
            SimpleServerPlayerData winner = arrayList.get(0);
            System.out.println("IS WINNER?"  + winner.getNick());

            for (SimpleServerPlayerData winnerFromArray:arrayList) {
                if (winnerFromArray.getScore() >= winner.getScore()){
                    winner = winnerFromArray;
                }
            }
                winner.setNick(winLogo+winner.getNick());
        }
        return allPlayersComplete;
    }

    private void gameEndings() {
        for (SimpleServerPlayerData playa:arrayList) {
            if (playa.isGameComplete()){
                playa.setNick("GAME COMPLETE: " + playa.getNick());
            }
        }
    }
}