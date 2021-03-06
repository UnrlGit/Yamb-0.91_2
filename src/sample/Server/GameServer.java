package sample.Server;

import sample.threads.ClearClientsServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameServer{
    private static ArrayList<SimpleServerPlayerData> playersArray = new ArrayList<>();
    private static String IMPLEMENTATION_NAME = "diceRollService";
    private static int RMI_PORT = 5532;



    public synchronized static void main(String[] args) {
        System.err.println("Server ready");
        try {
            NetworkHandler obj = new NetworkHandler();
            DiceRollService stub = (DiceRollService) UnicastRemoteObject.exportObject((Remote)obj, 0);
            Registry registry = LocateRegistry.createRegistry(RMI_PORT);
            registry.bind(IMPLEMENTATION_NAME, stub);
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
        runNetwork();
    }

    private static void runNetwork() {
        Thread thread = new Thread(new ClearClientsServerThread(playersArray));
        thread.start();
        try (ServerSocket serverSocket = new ServerSocket(4321)){
            while (true){
                new GameServerThread(serverSocket.accept(), playersArray).start();
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
