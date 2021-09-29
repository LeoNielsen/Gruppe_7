package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientHandler implements Runnable {
    private Socket client;
    private User user;
    private PrintWriter pw;
    private Scanner scanner;
    private String name;
    private BlockingQueue<Message> messages;
    private CopyOnWriteArrayList<User> users;
    private CopyOnWriteArrayList<ClientHandler> clients;

    public ClientHandler(Socket client, BlockingQueue<Message> messages, CopyOnWriteArrayList<ClientHandler> clients, CopyOnWriteArrayList<User> users) throws IOException {
        this.client = client;
        this.messages = messages;
        this.clients = clients;
        this.users = users;
        this.pw = new PrintWriter(client.getOutputStream(), true);
        this.scanner = new Scanner(client.getInputStream());
    }

    public User getUserByName(String name) {
        for (User u : users) {
            if (u.getName().equals(name)) {
                return u;
            }
        }
        return null;
    }

    public User getUser() {
        return user;
    }

    public void protocol() throws IOException {
        startUserLogin();
        String msg = "";
        while (true) {
            msg = scanner.nextLine();
            commands(msg);
        }
    }

    public void startUserLogin() throws IOException {
        pw.println("Do you have an user? (y/n)");
        String s = scanner.nextLine();
        if (s.equalsIgnoreCase("y")) {
            pw.println("Type your name?");
            name = scanner.nextLine();
            user = getUserByName(name);
            clients.add(this);
            if (user == null) {
                commands("CLOSE#2");
            } else {
                commands("CONNECT#" + name);
            }
        } else if (s.equalsIgnoreCase("n")) {
            pw.println("Hi what is your name?");
            name = scanner.nextLine();
            user = new User(name);
            users.add(user);
            clients.add(this);
            commands("CONNECT#" + name);
        } else {
            commands("CLOSE#1");
        }

    }

    public void commands(String msg) throws IOException {
        if (msg.contains("#")) {
            String[] strings = msg.split("#");
            String action = strings[0];
            String data = strings[strings.length - 1];

            switch (action) {
                case "SEND":
                    String receiver = strings[1];
                    messages.add(new Message(name, receiver, "MESSAGE#" + name + "#" + data));
                    break;
                case "CONNECT":
                case "DISCONNECT":
                    messages.add(new Message(data, "*", "ONLINE#" + getAllOnlineUsers()));
                    break;
                case "CLOSE":
                    if (data.equals("1")) {
                        pw.println("illegal input was received (" + msg + ")");

                    } else if (data.equals("2")) {
                        pw.println("User not found (" + msg + ")");

                    } else {
                        pw.println("Closing.. (" + msg + ")");
                    }
                    pw.println("CLOSE#");
                    synchronized (this) {
                        clients.remove(this);
                        commands("DISCONNECT#" + name);
                        client.close();
                    }

                    break;
                default:
                    pw.println("CLOSE#2");
                    break;
            }
        }
    }

    public String getAllOnlineUsers() {
        StringBuilder s = new StringBuilder();
        String n = ", ";
        for (int i = 0; i < clients.size(); i++) {
            if (i == clients.size() - 1) {
                n = "";
            }
            s.append(clients.get(i).getUser().getName() + n);
        }
        return s.toString();
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
