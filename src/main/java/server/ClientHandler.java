package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    Socket client;
    PrintWriter pw;
    Scanner scanner;

    public ClientHandler(Socket socket) throws IOException {
        this.client = socket;
        this.pw = new PrintWriter(client.getOutputStream(),true);
        this.scanner = new Scanner(client.getInputStream());
    }

    public void protocol(){

    }

    @Override
    public void run() {
        pw.println("Hello");
    }
}
