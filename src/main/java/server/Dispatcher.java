package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dispatcher implements Runnable{
    BlockingQueue<String> messages;
    CopyOnWriteArrayList<ClientHandler> clients;

    public Dispatcher(BlockingQueue<String> queue, CopyOnWriteArrayList<ClientHandler> clients){
        this.messages = queue;
        this.clients = clients;
    }

    public void addClient(ClientHandler client){
       clients.add(client);
        for (ClientHandler c : clients) {
            c.getPw().println("connected# " + client.getName());

        }
    }

    @Override
    public void run() {
        try {
            String outMsg = "";
            while (true){
                outMsg = messages.take();
                for (ClientHandler client : clients) {
                    client.getPw().println(outMsg);
                }

            }
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }

    }
}
