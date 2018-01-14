package sample.Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class GameServerThread extends Thread{
    private Socket socket = null;
    private ObjectInputStream inStream = null;
    private ArrayList<SimpleServerPlayerData> arrayList;

    public GameServerThread(Socket socket, ArrayList<SimpleServerPlayerData> playerData){
        this.socket = socket;
        this.arrayList = playerData;
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
}