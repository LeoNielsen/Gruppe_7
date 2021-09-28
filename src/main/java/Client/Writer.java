package Client;

import java.io.PrintWriter;
import java.util.Scanner;

class Writer extends Thread{
    Scanner keyboard;
    PrintWriter pw;
    boolean keepRunning = true;

    public Writer (Scanner Keyboard, PrintWriter pw){
        this.keyboard = Keyboard;
        this.pw = pw;
    }

    @Override
    public void run() {
        while(keepRunning){
            String msgToSend = keyboard.nextLine();
            pw.println(msgToSend);

            if (msgToSend.equals("CLOSE#")){
                keepRunning = false;
            }
        }
    }
}