package server;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class Main {


    //Call server with arguments like this: 8088
    public static void main(String[] args) throws IOException {
        int port = 8088;


        EchoServer echoServer = new EchoServer(port);
        echoServer.start();

        /**
        try {
            if (args.length == 1) {
                port = Integer.parseInt(args[0]);
            }
            else {
                throw new IllegalArgumentException("Server not provided with the right arguments");
            }
        } catch (NumberFormatException ne) {
            System.out.println("Illegal inputs provided when starting the server!");
            return;
        }*/

    }


}
