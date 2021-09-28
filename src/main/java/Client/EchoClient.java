package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;





public class EchoClient {

    Socket client;
    PrintWriter pw;
    Scanner scanner;

    public void connect(String address, int port) throws IOException {
        client = new Socket(address,port);
        pw = new PrintWriter(client.getOutputStream(), true);
        scanner = new Scanner(client.getInputStream());
        Reader reader = new Reader(scanner);
        reader.start();
        Scanner keyboard = new Scanner(System.in);
//        boolean keepRunning = true;

//        while(keepRunning){
//            String msgToSend = keyboard.nextLine();
//            pw.println(msgToSend);
//
//            if (msgToSend.equals("CLOSE#")){
//                keepRunning = false;
//            }
//        }
        Writer writer = new Writer(keyboard,pw);
        writer.start();

        pw.println("CLOSE#");
        client.close();
    }

    public static void main(String[] args) throws IOException {

        new EchoClient().connect("localhost",2345);



    }
}