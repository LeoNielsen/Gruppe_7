package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class EchoServer {

    private int port;
    private CopyOnWriteArrayList<ClientHandler> clients;
    private BlockingQueue<String> msg;

    public EchoServer(int port) {
        this.port = port;
        this.clients = new CopyOnWriteArrayList<>();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        clients = new CopyOnWriteArrayList<>();
        msg = new ArrayBlockingQueue<>(10);
        Dispatcher dispatcher = new Dispatcher(msg, clients);
        while (true) {
            Socket client = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(client, dispatcher);
            new Thread(clientHandler).start();
        }
    }

}
