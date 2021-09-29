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
    private CopyOnWriteArrayList<User> users;
    private BlockingQueue<Message> msg;

    public EchoServer(int port) {
        this.port = port;
        this.clients = new CopyOnWriteArrayList<>();
        this.users = new CopyOnWriteArrayList<>();
        this.msg = new ArrayBlockingQueue<>(10);
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Dispatcher dispatcher = new Dispatcher(msg, clients);
        while (true) {
            Socket client = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(client, msg, clients, users);
            new Thread(clientHandler).start();
            new Thread(dispatcher).start();
        }
    }

}
