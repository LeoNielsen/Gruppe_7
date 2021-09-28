package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dispatcher implements Runnable {
    BlockingQueue<Message> messages;
    CopyOnWriteArrayList<ClientHandler> clients;

    public Dispatcher(BlockingQueue<Message> messages, CopyOnWriteArrayList<ClientHandler> clients) {
        this.messages = messages;
        this.clients = clients;
    }

    public BlockingQueue<Message> getMessages() {
        return messages;
    }

    public void sendMessage(Message message) {
        if (message.getReceiver().equals("*")) {
            for (ClientHandler client : clients) {
                client.getPw().println(message.getMessage());
            }
        } else {
            getClient(message.getReceiver()).getPw().println(message.getMessage());
        }
    }

    public ClientHandler getClient(String name) {
        for (ClientHandler c : clients) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void run() {
        try {
            Message outMsg = null;
            while (true) {
                outMsg = messages.take();
                sendMessage(outMsg);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }
}
