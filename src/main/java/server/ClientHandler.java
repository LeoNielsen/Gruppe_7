package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private Socket client;
    private PrintWriter pw;
    private Scanner scanner;
    private String name;

    public ClientHandler(Socket socket) throws IOException {
        this.client = socket;
        this.pw = new PrintWriter(client.getOutputStream(),true);
        this.scanner = new Scanner(client.getInputStream());
    }

    public void protocol() {
        pw.println("Hi what is your name?");
        name = scanner.nextLine();
        pw.println("CONNECT#" + name);
    }

    @Override
    public void run() {
        protocol();
    }
}
