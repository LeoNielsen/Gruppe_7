package Client;

import java.io.PrintWriter;
import java.util.Scanner;

class Reader extends Thread{

    boolean keepRunning = true;
    Scanner scFromServer;


    public Reader (Scanner scFromServer){
        this.scFromServer = scFromServer;
    }

    //TODO: optimer!

    @Override
    public void run() {
        while (keepRunning) {
            String output = scFromServer.nextLine();
            System.out.println(output);
            if(output.equals("CLOSE#")){
                keepRunning = false;
            }
        }
        scFromServer.close();
    }
}