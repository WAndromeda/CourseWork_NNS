import Entity.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

    public final static int PORT = 80;
    public final static String IP_ADDRESS = "http://localhost";
    private static int ClientConnected = 0;
    private ServerSocket serverSocket;
    private Socket client = null;
    public static LinkedList<Client> clients;

    public Server() {
        serverSocket = null;
        client = null;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("\nServer started on PORT: " + PORT + "\n");
            try {
                while (true) {
                    client = serverSocket.accept();
                    addClient();
                    Runnable run = new ThreadConnectedClient(client, ClientConnected);
                    Thread clientThread = new Thread(run);
                    clientThread.start();
                }
            } finally {

                if (client != null)
                    client.close();
                serverSocket.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    public static void addClient() {
        ClientConnected++;
    }

    public static void removeClient() {
        ClientConnected--;
    }

    public static int getClientConnected() {
        return ClientConnected;
    }
}