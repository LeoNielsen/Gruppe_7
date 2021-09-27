package server;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dispatcher implements Runnable{
    BlockingQueue<String> messages;
    CopyOnWriteArrayList<ClientHandler>sockets;

    public Dispatcher(BlockingQueue<String> queue, CopyOnWriteArrayList<ClientHandler> clients){
        this.messages = queue;
        this.sockets = clients;
    }

    @Override
    public void run() {
        try {
            String outMsg = "";
            while (true){
                outMsg = messages.take();
                for (ClientHandler client : sockets) {
                    client.getPw().println(outMsg);
                }

            }
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }

    }
}
