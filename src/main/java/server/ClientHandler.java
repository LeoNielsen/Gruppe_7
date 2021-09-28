package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable {
    private Socket client;
    private PrintWriter pw;
    private Scanner scanner;
    private String name;
    //private Dispatcher disp;
    private BlockingQueue<Message> messages;

//    public ClientHandler(Socket socket, Dispatcher disp) throws IOException {
//        this.client = socket;
//        this.disp = disp;
//        this.pw = new PrintWriter(client.getOutputStream(), true);
//        this.scanner = new Scanner(client.getInputStream());
//    }

    public ClientHandler(Socket client, BlockingQueue<Message> messages) throws IOException {
        this.client = client;
        this.messages = messages;
        this.pw = new PrintWriter(client.getOutputStream(), true);
        this.scanner = new Scanner(client.getInputStream());
    }

    public void protocol() throws IOException {
        pw.println("Hi what is your name?");
        name = scanner.nextLine();
        messages.add(new Message(name,"*", "CONNECTED# " + name));
        //disp.addClient(this);
        String msg = "";
        while (!msg.equals("CLOSE#")) {
            msg = scanner.nextLine();
            if (msg.contains("#")) {

                String[] strings = msg.split("#");
                String action = strings[0];
                String data = strings[strings.length-1];

                switch (action) {
                    case "SEND":
                        String receiver = strings[1];
                        messages.add(new Message(name, receiver, data));
                        break;
                    case "CLOSE":
                        pw.println("CLOSE#");
                        break;
                }
            }
        }
        client.close();
    }

    public PrintWriter getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            protocol();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
