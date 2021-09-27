package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket client;
    private PrintWriter pw;
    private Scanner scanner;
    private String name;
    private Dispatcher disp;

    public ClientHandler(Socket socket, Dispatcher disp) throws IOException {
        this.client = socket;
        this.disp = disp;
        this.pw = new PrintWriter(client.getOutputStream(), true);
        this.scanner = new Scanner(client.getInputStream());
    }

    public void protocol() throws IOException {
        pw.println("Hi what is your name?");
        name = scanner.nextLine();
        disp.addClient(this);
        String msg = "";
        while (!msg.equals("CLOSE#")) {
            msg = scanner.nextLine();
            if (msg.contains("#")) {
//                String[] strings = splitMessage(msg);
//                String action = strings[0];
//                String s = strings[1];
//                switch (action) {
//                    case "UPPER":
//                        writer.println(s.toUpperCase());
//                        break;
//                    case "LOWER":
//                        writer.println(s.toLowerCase());
//                        break;
//                    case "REVERSE":
//                        char[] chars = msg.toCharArray();
//                        String reverse = "";
//                        for (int i = chars.length-1; i > -1; i--) {
//                            reverse = reverse + chars[i];
//                        }
//                        writer.println(reverse);
//                        break;
//                    case "ALL":
//                        messages.add(s);
//                        //TODO: indsæt besked i delt ressource
//                        break;
//                    case "QUIZ":
//                        doQuiz();
//                        break;
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
