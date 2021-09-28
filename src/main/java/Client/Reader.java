package Client;

import java.util.Scanner;

class Reader extends Thread{

    boolean keepRunning = true;
    Scanner scFromServer;

    public Reader (Scanner scFromServer){
        this.scFromServer = scFromServer;
    }

    @Override
    public void run() {
        while (keepRunning) {
            System.out.println(scFromServer.nextLine());
        }
    }
}