package Client;

import java.io.PrintWriter;
import java.util.Scanner;

class Reader extends Thread{

    boolean keepRunning = true;
    Scanner scFromServer;
    PrintWriter pw;

    public Reader (Scanner scFromServer, PrintWriter pw){
        this.scFromServer = scFromServer;
        this.pw = pw;
    }

    @Override
    public void run() {
        while (keepRunning) {
            System.out.println(scFromServer.nextLine());
        }
    }
}